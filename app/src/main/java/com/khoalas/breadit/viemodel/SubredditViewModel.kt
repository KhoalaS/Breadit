package com.khoalas.breadit.viemodel

import androidx.lifecycle.ViewModel
import com.khoalas.breadit.data.repo.SubredditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubredditViewModel @Inject constructor(private val subredditRepository: SubredditRepository) : ViewModel() {
    val subredditInfo = subredditRepository.subredditInfo

    suspend fun fetchSubredditInfoByName(name: String) {
        subredditRepository.loadSubredditInfo(name)
    }
}