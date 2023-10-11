package polis.app.movies.data.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import polis.app.movies.data.model.FavoriteRequestBody
import polis.app.movies.domain.MovieRepository

class MovieRepositoryImpl(private val movieRepository: MovieRepository) {

    suspend fun nowPlaying() = movieRepository.nowPlaying()

    suspend fun nowPlayingPaged(page: Int) = movieRepository.nowPlayingPaged(page)

    suspend fun favoriteMovies() = movieRepository.favoriteMovies()

    suspend fun addToFavorite(favoriteRequestBody: FavoriteRequestBody) =
        movieRepository.addToFavorite(favoriteRequestBody)

    suspend fun searchMovie(query: String) = movieRepository.searchMovie(query)
    fun getPagedNowPlaying(searchQuery: String) = Pager(
        config = PagingConfig(
            pageSize = 50,
            initialLoadSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(this, searchQuery) }
    ).flow
}