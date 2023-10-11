package polis.app.movies.utils

import okhttp3.Interceptor

class AuthInterceptor(private val organization: String? = null, private val accessToken: String) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()

        request = if (organization != null) {
            request
                .newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        } else
            request
                .newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

        return chain.proceed(request)
    }
}