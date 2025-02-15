package com.khoalas.breadit.viemodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.ApolloClient
import com.khoalas.breadit.apollo.SubredditInfoByNameQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubredditViewModel @Inject constructor(private val apolloClient: ApolloClient) : ViewModel() {
    suspend fun getSubredditInfoByName(name: String) {
        val response = apolloClient.query(
            SubredditInfoByNameQuery(
                subredditName = "cats",
                includeRecapFields = false,
                includeWelcomePage = true,
                includeCommunityGold = false,
                includeCommunityLeaderboard = true,
                includeMomentFeatures = true
            )
        ).execute()
        Log.d("getSubredditInfoByName", "Success ${response.data}")
    }
}