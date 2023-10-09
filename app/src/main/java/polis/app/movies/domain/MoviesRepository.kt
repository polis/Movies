package polis.app.movies.domain

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoviesRepository {

    @GET("/movie/now_playing")
    suspend fun nowPlaying(@Query("api_key") token: String): Response<ResponseBody>
}