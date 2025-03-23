package com.khoalas.breadit.ui.nav.bottom

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.khoalas.breadit.ui.screens.LoginScreen

class HomeNode(
    buildContext: BuildContext
) : Node(
    buildContext = buildContext
) {
    @Composable
    override fun View(modifier: Modifier) {
        LoginScreen()
    }
}