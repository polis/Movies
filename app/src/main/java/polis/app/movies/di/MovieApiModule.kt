package polis.app.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import polis.app.movies.BuildConfig
import polis.app.movies.data.movie.MovieRepositoryImpl
import polis.app.movies.domain.MovieRepository
import polis.app.movies.utils.AuthInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieApiModule {

//    private const val BASE_URL = "https://api.themoviedb.org/3/"
//    private const val accessToken: String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1N2RhMDVmZWY5MmZhZWQwZGJhM2I2MGRkYWNmMWM2OSIsInN1YiI6IjY1MjQ3ODZhNDQ3ZjljMDBjNmJjODAxYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kr350KVwU8WIeYZI2LwFBQ8mM8r0ODy2K3CI0EWJF1s"
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(accessToken = BuildConfig.API_KEY))
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun providesRetrofitClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesMovieRepository(retrofit: Retrofit): MovieRepositoryImpl =
        MovieRepositoryImpl(retrofit.create(MovieRepository::class.java))

}