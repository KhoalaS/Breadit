package com.khoalas.breadit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khoalas.breadit.ui.screens.LoginScreen
import com.khoalas.breadit.ui.screens.SubredditAboutScreen
import com.khoalas.breadit.ui.screens.SubredditScreen
import com.khoalas.breadit.ui.theme.BreaditTheme
import com.khoalas.breadit.viemodel.AuthViewModel
import com.khoalas.breadit.viemodel.state.LoginState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authViewModel: AuthViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            val loginState by authViewModel.loginState.collectAsState()

            BreaditTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    if (loginState == LoginState.LOGGED_IN){
                        var selectedItem by rememberSaveable { mutableIntStateOf(0) }
                        val items = listOf("Home", "Messages")
                        val iconsFilled = listOf(Icons.Filled.Home, Icons.Filled.Email)
                        val iconsOutline = listOf(Icons.Outlined.Home, Icons.Outlined.Email)

                        NavigationBar() {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItem == index,
                                    onClick = { selectedItem = index },
                                    icon = {
                                        Icon(
                                            if (selectedItem == index) iconsFilled[index] else iconsOutline[index],
                                            contentDescription = item
                                        )
                                    },
                                    label = {
                                        Text(item)
                                    }
                                )
                            }
                        }
                    }
                }) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        when (loginState) {
                            is LoginState.LOADING -> LoadingSpinner()
                            is LoginState.LOGGED_IN -> SubredditScreen()
                            is LoginState.LOGGED_OUT -> LoginScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingSpinner(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BreaditTheme {
        Greeting("Android")
    }
}