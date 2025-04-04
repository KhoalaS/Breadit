package com.khoalas.breadit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khoalas.breadit.viemodel.SubredditViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: SubredditViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    var tabState by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Popular")

    Column(modifier = Modifier.fillMaxSize()) {
        PrimaryTabRow(selectedTabIndex = tabState) {
            items.forEachIndexed { index, item ->
                Tab(selected = tabState == index,
                    onClick = { tabState = index },
                    text = { Text(text = item, maxLines = 2, overflow = TextOverflow.Ellipsis) })
            }
        }

        Text("Logged in")

    }

}

