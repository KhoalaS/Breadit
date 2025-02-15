package com.khoalas.breadit.ui.screens

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khoalas.breadit.viemodel.SubredditViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: SubredditViewModel = viewModel()){
    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {
        coroutineScope.launch {
            viewModel.getSubredditInfoByName("cats")

        }
    }){
        Text("Button")
    }
    Text("Logged in")
}

