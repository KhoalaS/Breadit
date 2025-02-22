package com.khoalas.breadit.viemodel

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khoalas.breadit.data.repo.SubredditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SubredditViewModel @Inject constructor(private val subredditRepository: SubredditRepository) :
    ViewModel() {
    private val subredditInfo = subredditRepository.subredditInfo
    private val subredditStyles = subredditRepository.subredditStyles

    val idCardWidget = subredditRepository.idCardWidget

    val subredditData = subredditInfo.map { it?.subredditDataDetailsFragment }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val isSubscribed: StateFlow<Boolean?> =
        subredditInfo.map { it?.subredditDataDetailsFragment?.isSubscribed }
            .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val styles =
        subredditStyles.map { it?.styles?.subredditStylesFragment }
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    suspend fun fetchSubredditInfoAndStyles(name: String) {
        subredditRepository.loadSubredditInfoAndStyles(name)
    }
}