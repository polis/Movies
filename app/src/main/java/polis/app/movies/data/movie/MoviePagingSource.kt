package polis.app.movies.data.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import polis.app.movies.data.model.MovieInfo

class MoviePagingSource(
    private val movieRepository: MovieRepositoryImpl,
    private val searchQuery: String
): PagingSource<Int, MovieInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieInfo> {
        val page = params.key ?: 1

        return try {

            val movies = if (searchQuery.isEmpty()) {
                (movieRepository.nowPlayingPaged(page).body()?.results ?: listOf())
            } else {
                (movieRepository.searchMovie(searchQuery).body()?.results ?: listOf())
            }

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}