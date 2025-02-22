package com.khoalas.breadit.gql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Query
import com.khoalas.breadit.auth.AuthRepository
import com.khoalas.breadit.auth.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val auth: AuthRepository) : Interceptor {
    // TODO actually get the available codecs

    /* TODO make some of the headers configurable
        maybe device presets?
    * */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                auth.authToken.value?.let { token ->
                    addHeader("Authorization", "bearer $token")
                }
            }
            .apply {
                auth.deviceId.value?.let { id ->
                    addHeader("client-vendor-id", id)
                    addHeader("x-reddit-device-id", id)
                }
            }
            .addHeader("__temp_suppress_gql_request_latency_seconds", "true")
            .addHeader("User-Agent", Constants.USER_AGENT)
            .addHeader(
                "x-reddit-media-codecs",
                "available-codecs=video/avc, video/hevc, video/x-vnd.on2.vp9"
            )
            .addHeader("device-name", "samsung;SM-A405FN")
            .addHeader("x-reddit-dpr", "3.0")
            .addHeader("x-reddit-width", "360")
            .build()
        return chain.proceed(request)
    }
}

// TODO logging
suspend fun <D : Query.Data> makeApolloRequest(
    client: ApolloClient,
    query: Query<D>
): ApolloResponse<D> {
    return client.query(query).addHttpHeader("x-apollo-operation-name", query.name())
        .addHttpHeader("x-apollo-operation-id", query.id())
        .addHttpHeader("x-reddit-compression", "1").execute()
}