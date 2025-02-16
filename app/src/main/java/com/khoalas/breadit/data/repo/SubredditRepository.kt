package com.khoalas.breadit.data.repo

import com.apollographql.apollo.ApolloClient
import com.khoalas.breadit.apollo.SubredditInfoByNameQuery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SubredditRepository(private val apolloClient: ApolloClient) {
    // TODO post data + info (used for styling + about info)
    private val _subredditInfo =
        MutableStateFlow<SubredditInfoByNameQuery.SubredditInfoByName?>(null)
    val subredditInfo = _subredditInfo.asStateFlow()

    suspend fun loadSubredditInfo(name: String) {
        val response = apolloClient.query(
            SubredditInfoByNameQuery(
                subredditName = name,
                includeRecapFields = false,
                includeWelcomePage = true,
                includeCommunityGold = false,
                includeCommunityLeaderboard = true,
                includeMomentFeatures = true
            )
        ).execute()
        if (response.data?.subredditInfoByName != null) {
            _subredditInfo.value = response.data!!.subredditInfoByName
        }
    }
}