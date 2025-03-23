package com.khoalas.breadit.ui.nav.bottom

sealed class TabTarget() {
    object Home: TabTarget()
    object Messages: TabTarget()
    object All: TabTarget()
}