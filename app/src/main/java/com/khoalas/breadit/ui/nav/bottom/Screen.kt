package com.khoalas.breadit.ui.nav.bottom

sealed class NavTarget() {
    object Home: NavTarget()
    object Messages: NavTarget()
    object All: NavTarget()
}