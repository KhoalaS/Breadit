package com.khoalas.breadit.modules

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.khoalas.breadit.auth.AuthRepository
import com.khoalas.breadit.gql.AuthorizationInterceptor
import com.khoalas.breadit.auth.RedditAuth
import com.khoalas.breadit.auth.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    @Provides
    @Singleton
    fun provideRedditAuth(okHttpClient: OkHttpClient): RedditAuth {
        return RedditAuth(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(sessionManager: SessionManager, auth: RedditAuth): AuthRepository {
        return AuthRepository(sessionManager, auth)
    }

    @Provides
    @Singleton
    fun provideApolloClient(authRepository: AuthRepository): ApolloClient {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://gql-fed.reddit.com")
            .enableAutoPersistedQueries(true)
            .sendApqExtensions(true)
            .sendDocument(false)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(AuthorizationInterceptor(authRepository))
                    .build()
            )
            .build()
        return apolloClient
    }
}