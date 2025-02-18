package com.khoalas.breadit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.khoalas.breadit.LoadingSpinner
import com.khoalas.breadit.viemodel.SubredditViewModel

@Composable
fun SubredditScreen(viewModel: SubredditViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val subredditInfo by viewModel.subredditInfo.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSubredditInfoByName("cats")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (subredditInfo != null) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = subredditInfo!!.subredditDataDetailsFragment.styles?.mobileBannerImage,
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = Color(0xFFFE3F00))
                        .size(50.dp)
                        .padding(4.dp),
                    model = subredditInfo!!.subredditDataDetailsFragment.styles?.icon,
                    contentDescription = null
                )
                Column {
                    Text(subredditInfo!!.subredditDataDetailsFragment.prefixedName!!)
                    Text(subredditInfo!!.subredditDataDetailsFragment.prefixedName!!)
                }
            }
            Text(text = subredditInfo!!.subredditDataDetailsFragment.subscribersCount.toString())
        } else {
            LoadingSpinner(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}