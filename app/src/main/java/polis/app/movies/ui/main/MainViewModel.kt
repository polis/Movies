package polis.app.movies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import polis.app.movies.data.model.FavoriteRequestBody
import polis.app.movies.data.model.MovieInfo
import polis.app.movies.data.model.Movies
import polis.app.movies.data.movie.MovieRepositoryImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepositoryImpl
) : ViewModel() {

    val isLoading = MutableStateFlow(false)

    private val _movies = MutableStateFlow<Movies>(Movies())
    val movies = _movies.asStateFlow()

    private val _selectedMovie = MutableStateFlow<MovieInfo?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    private val _pagedMovies: MutableStateFlow<PagingData<MovieInfo>> =
        MutableStateFlow(PagingData.empty())
    val pagedMovies: StateFlow<PagingData<MovieInfo>> = _pagedMovies.asStateFlow()

    val favoriteMovies = MutableStateFlow<List<Int>>(listOf())

    val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            Timber.d("!!! Start fetching data")
//            isLoading.value = true
//            val response = movieRepository.nowPlaying()
//
//            when {
//                response.isSuccessful -> {
//                    val responseBody = response.body()
//                    _movies.value = Movies(
//                        dates = responseBody?.dates ?: Dates(),
//                        responseBody?.page ?: 0,
//                        responseBody?.results ?: listOf(),
//                        responseBody?.totalPages ?: 0,
//                        responseBody?.totalResults ?: 0
//                    )
//                    Timber.d("!!! Movies: ${movies.value}")
//                }
//
//                else -> {
//                    Timber.d("!!! Error: ${response.errorBody()}")
//                }
//
//            }
//            isLoading.value = false
//
//        }

        getPagedMovies()
        loadFavoriteMovies()
    }
    fun selectMovie(movie: MovieInfo) {
        _selectedMovie.value = movie
    }

    fun clearSelectedMovie() {
        _selectedMovie.value = null
    }

    fun addToFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val response = movieRepository.addToFavorite(
                FavoriteRequestBody(
                    favorite = !isMovieAddedToFavorites(movieId), mediaId = movieId
                )
            )
            when {
                response.isSuccessful -> {
                    loadFavoriteMovies()
                    // Now we need to recompose the item in the list
                    // to update the icon
                    val movie = movies.value.results.find { it.id == movieId }
                    movie?.let {
                        val index = movies.value.results.indexOf(it)
                        val updatedMovie = it.copy()
                        val updatedMovies = movies.value.results.toMutableList()
                        updatedMovies[index] = updatedMovie
                        _movies.value = movies.value.copy(results = updatedMovies)
                    }
                    Timber.d("!!! Movies: ${movies.value}")
                }

                else -> {
                    Timber.d("!!! Error: ${response.errorBody()}")
                }

            }
        }
    }

    private fun getPagedMovies( searchQuery: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            movieRepository.getPagedNowPlaying( searchQuery)
                .cachedIn(viewModelScope)
                .collect {
                    _pagedMovies.value = it
                    Timber.d("!!! Paged Movies are loaded")
                    isLoading.value = false
                }
        }
    }

    private fun isMovieAddedToFavorites(movieId: Int): Boolean {
        return favoriteMovies.value.any { it == movieId }
    }

    private fun loadFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = movieRepository.favoriteMovies()
            when {
                response.isSuccessful -> {
                    val responseBody = response.body()
                    favoriteMovies.value = responseBody?.results?.map { it.id } ?: listOf()

                    Timber.d("!!! Movies: ${movies.value}")
                }

                else -> {
                    Timber.d("!!! Error: ${response.errorBody()}")
                }
            }
        }
    }

    fun search (searchValue: String) {
        viewModelScope.launch {
            Timber.d("searchValue = $searchValue")
            _searchQuery.tryEmit(searchValue)
            getPagedMovies(searchValue)
        }
    }

}