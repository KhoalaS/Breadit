package com.khoalas.breadit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khoalas.breadit.viemodel.SubredditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubredditAboutScreen(viewModel: SubredditViewModel = viewModel()) {
    var tabState by remember { mutableIntStateOf(0) }
    val tabs = listOf("About", "Menu")
    val subredditData by viewModel.subredditData.collectAsState()


    Column(modifier = Modifier.fillMaxSize()) {
        Text("Anderer Text")

        if (subredditData != null) {
            Text(text = subredditData?.id ?: "Empty id")
        } else {
            Text("loading")
        }

        PrimaryTabRow(selectedTabIndex = tabState) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabState == index,
                    onClick = { tabState = index },
                    text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                )
            }
        }
    }

}