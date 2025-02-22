package com.khoalas.breadit.data.repo

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.khoalas.breadit.apollo.SubredditInfoByNameQuery
import com.khoalas.breadit.apollo.SubredditStructuredStyleQuery
import com.khoalas.breadit.gql.makeApolloRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SubredditRepository(private val apolloClient: ApolloClient) {
    // TODO post data + info (used for styling + about info)
    private val _subredditInfo =
        MutableStateFlow<SubredditInfoByNameQuery.SubredditInfoByName?>(null)
    val subredditInfo = _subredditInfo.asStateFlow()

    private val _subredditStyles =
        MutableStateFlow<SubredditStructuredStyleQuery.OnSubreddit?>(null)
    val subredditStyles = _subredditStyles.asStateFlow()

    private val _idCardWidget =
        MutableStateFlow<SubredditStructuredStyleQuery.OrderedSidebarWidget?>(null)
    val idCardWidget = _idCardWidget.asStateFlow()

    suspend fun loadSubredditInfo(name: String) {
        val response = makeApolloRequest(
            apolloClient, SubredditInfoByNameQuery(
                subredditName = name,
                loggedOutIsOptedIn = Optional.present(false),
                filterGated = Optional.present(true),
                includeRecapFields = false,
                includeWelcomePage = true,
                includeCommunityGold = false,
                includeCommunityLeaderboard = true,
                includeMomentFeatures = true
            )
        )
        if (response.data?.subredditInfoByName != null) {
            _subredditInfo.value = response.data!!.subredditInfoByName
        }
    }

    suspend fun loadSubredditStructuredStyles(name: String) {
        val response =
            makeApolloRequest(
                apolloClient,
                SubredditStructuredStyleQuery(
                    subredditName = name,
                    includeWidgets = Optional.present(true),
                    includeMediaAuth = Optional.present(false),
                    includeExtendedVideoAsset = Optional.present(false)
                )
            )
        if (response.data != null) {
            _subredditStyles.value = response.data!!.subredditInfoByName?.onSubreddit

            Log.d("STYLE", response.data!!.subredditInfoByName.toString())

            _idCardWidget.value =
                response.data!!.subredditInfoByName?.onSubreddit?.widgets?.orderedSidebarWidgets?.find {
                    it?.__typename == "IdCardWidget"
                }
        }
    }

    suspend fun loadSubredditInfoAndStyles(name: String) {
        loadSubredditStructuredStyles(name)
        loadSubredditInfo(name)
    }
}