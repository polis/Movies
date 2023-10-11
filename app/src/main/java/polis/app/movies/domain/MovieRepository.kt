package polis.app.movies.domain

import polis.app.movies.data.model.FavoriteRequestBody
import polis.app.movies.data.model.Movies
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieRepository {

    @GET("movie/now_playing")
    suspend fun nowPlaying(): Response<Movies>

    @GET("movie/now_playing")
    suspend fun nowPlayingPaged(@Query("page") page: Int): Response<Movies>

    @GET("account/20551250/favorite/movies")
    suspend fun favoriteMovies(): Response<Movies>

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): Response<Movies>

    @POST("account/20551250/favorite")
    suspend fun addToFavorite(@Body favoriteRequestBody: FavoriteRequestBody): Response<Movies>


}