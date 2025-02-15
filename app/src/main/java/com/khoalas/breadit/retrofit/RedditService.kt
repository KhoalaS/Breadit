package com.khoalas.breadit.retrofit

import com.khoalas.breadit.auth.LoginRequest
import com.khoalas.breadit.data.model.AccesTokenResponse
import com.khoalas.breadit.data.model.LoginResult
import com.khoalas.breadit.data.model.Scope
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RedditService {
    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest, @HeaderMap headers: Map<String, String>) : Response<LoginResult>

    @POST("/api/access_token")
    suspend fun getAccessToken(
        @HeaderMap headers: Map<String, String>,
        @Body body: Scope
    ): Response<AccesTokenResponse>
}