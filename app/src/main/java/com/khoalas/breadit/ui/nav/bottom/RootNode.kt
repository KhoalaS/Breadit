package com.khoalas.breadit.ui.nav.bottom

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.navigation.transition.ModifierTransitionHandler
import com.bumble.appyx.core.navigation.transition.TransitionSpec
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.node.node
import com.bumble.appyx.navmodel.spotlight.Spotlight
import com.bumble.appyx.navmodel.spotlight.current
import com.bumble.appyx.navmodel.spotlight.operation.activate
import com.bumble.appyx.navmodel.spotlight.transitionhandler.SpotlightSlider
import com.khoalas.breadit.viemodel.AuthViewModel

class RootNode(
    buildContext: BuildContext,
    private val authViewModel: AuthViewModel,
    private val spotlight: Spotlight<TabTarget> = Spotlight(
        items = listOf(TabTarget.Home, TabTarget.Messages, TabTarget.All),
        initialActiveIndex = 0,
        savedStateMap = buildContext.savedStateMap
    )
) : ParentNode<TabTarget>(
    buildContext = buildContext,
    navModel = spotlight
) {
    @Composable
    override fun View(modifier: Modifier) {
        val loginState by authViewModel.loginState.collectAsState()

        val current by spotlight.current().collectAsState(TabTarget.Home)
        Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = current == TabTarget.Home,
                    onClick = { spotlight.activate(0) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
                )
                NavigationBarItem(
                    selected = current == TabTarget.Messages,
                    onClick = { spotlight.activate(1) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
                )
                NavigationBarItem(
                    selected = current == TabTarget.All,
                    onClick = { spotlight.activate(2) },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
                )
            }
        }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Children(navModel = navModel)
            }
        }
    }

    override fun resolve(navTarget: TabTarget, buildContext: BuildContext): Node =
        when (navTarget) {
            TabTarget.Home -> HomeNode(buildContext)
            TabTarget.Messages -> MessagesNode(buildContext)
            TabTarget.All -> node(buildContext) { Text(text = "Placeholder for child 3") }
        }
}

@Composable
fun <NavTarget> myRememberSpotlightSlider(
    transitionSpec: TransitionSpec<Spotlight.State, Offset> = { spring(stiffness = Spring.StiffnessVeryLow) },
    clipToBounds: Boolean = false
): ModifierTransitionHandler<NavTarget, Spotlight.State> = remember {
    SpotlightSlider(transitionSpec = transitionSpec, clipToBounds = clipToBounds)
}