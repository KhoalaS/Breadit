package com.khoalas.breadit.modules

import com.apollographql.apollo.ApolloClient
import com.khoalas.breadit.data.repo.SubredditRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewmodelModule {
    @Provides
    fun provideSubredditRepository(apolloClient : ApolloClient): SubredditRepository{
        return SubredditRepository(apolloClient)
    }
}