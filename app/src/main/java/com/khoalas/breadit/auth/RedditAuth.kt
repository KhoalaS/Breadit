package com.khoalas.breadit.auth

import android.util.Base64
import com.khoalas.breadit.retrofit.RedditService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

class RedditAuth (
    private val okHttpClient: OkHttpClient // Injected OkHttpClient
) {

    val api: RedditService by lazy {
        Retrofit.Builder()
            .baseUrl("https://accounts.reddit.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // Use the injected OkHttpClient
            .build()
            .create(RedditService::class.java)
    }

    fun getBasicAuthHeaders(): MutableMap<String, String>{
        val randomRate = 25 + Math.random() * 5
        val headers: MutableMap<String, String> = HashMap()
        val credentials = java.lang.String.format("%s:%s", Constants.CLIENT_ID, "")
        val auth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        headers["Authorization"] = auth
        headers["User-Agent"] = Constants.USER_AGENT
        headers["x-reddit-compression"] = "1"
        headers["x-reddit-qos"] =
            "down-rate-mbps=" + String.format(Locale.US, "%,.3f", randomRate)
        headers["x-reddit-retry"] = "algo=no-retries"
        headers["x-reddit-media-codecs"] =
            "available-codecs=video/avc, video/hevc, video/x-vnd.on2.vp9"
        return headers
    }
}

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Proceed with the request to get the response
        val response = chain.proceed(chain.request())

        // TODO check expiry and refresh token
        if (false){
        }

        // Return the response to the app
        return response
    }
}