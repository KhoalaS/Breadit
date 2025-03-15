package com.khoalas.breadit.ui.nav.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.node.node
import com.bumble.appyx.navmodel.spotlight.Spotlight
import com.bumble.appyx.navmodel.spotlight.operation.activate

class RootNode(
    buildContext: BuildContext,
    private val spotlight: Spotlight<NavTarget> = Spotlight(
        items = listOf(NavTarget.Home, NavTarget.Messages, NavTarget.All),
        initialActiveIndex = 0,
        savedStateMap = buildContext.savedStateMap
    )
) : ParentNode<NavTarget>(
    buildContext = buildContext,
    navModel = spotlight
) {
    @Composable
    override fun View(modifier: Modifier) {
        NavigationBar {
            NavigationBarItem(
                selected = false,
                onClick = { spotlight.activate(0) },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
            )
            NavigationBarItem(
                selected = false,
                onClick = { spotlight.activate(1) },
                icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
            )
            NavigationBarItem(
                selected = false,
                onClick = { spotlight.activate(2) },
                icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
            )
        }
        Children(navModel = navModel)
    }

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node =
        when (navTarget) {
            NavTarget.Home -> node(buildContext) { Text(text = "Placeholder for child 1") }
            NavTarget.Messages -> node(buildContext) { Text(text = "Placeholder for child 2") }
            NavTarget.All -> node(buildContext) { Text(text = "Placeholder for child 3") }
        }
}