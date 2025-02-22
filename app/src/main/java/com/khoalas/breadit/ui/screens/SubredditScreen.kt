package com.khoalas.breadit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.apollographql.apollo.api.label
import com.khoalas.breadit.LoadingSpinner
import com.khoalas.breadit.viemodel.SubredditViewModel

@Composable
fun SubredditScreen(viewModel: SubredditViewModel = hiltViewModel()) {
    val subredditData by viewModel.subredditData.collectAsState()
    val styles by viewModel.styles.collectAsState()
    val idCardWidget by viewModel.idCardWidget.collectAsState()
    val isSubscribed by viewModel.isSubscribed.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSubredditInfoAndStyles("cats")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (subredditData != null) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = styles?.mobileBannerImage,
                contentDescription = null
            )
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            color = Color(
                                (styles?.primaryColor
                                    ?: "#FFFFFFFF").toColorInt()
                            )
                        )
                        .size(50.dp)
                        .padding(4.dp),
                    model = styles?.icon,
                    contentDescription = null
                )
                Column {
                    Text(
                        text = subredditData?.prefixedName!!,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("${subredditData?.subscribersCount} ${idCardWidget?.widgetFragment?.idCardWidgetFragment?.subscribersText ?: "Subscribers"}")
                    Text("${subredditData?.activeCount ?: 0} ${idCardWidget?.widgetFragment?.idCardWidgetFragment?.currentlyViewingText ?: "Online"}")
                }
                isSubscribed?.let { subscribed ->
                    if (subscribed) {
                        Button(onClick = {}, contentPadding = PaddingValues(8.dp, 2.dp)) {
                            Text("Joined")
                        }
                    } else {
                        Button(onClick = {}) {
                            Text("Join")
                        }
                    }
                }


            }
            Text(subredditData?.publicDescriptionText ?: "")
        } else {
            LoadingSpinner(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun PostsColumn() {
    LazyColumn {

    }
}