package com.khoalas.breadit.gql

import com.khoalas.breadit.auth.AuthRepository
import com.khoalas.breadit.auth.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(private val auth: AuthRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                auth.authToken.value.let { token ->
                    addHeader("Authorization", "bearer $token")
                }
            }
            .addHeader("User-Agent", Constants.USER_AGENT)
            .build()
        return chain.proceed(request)
    }
}