package com.khoalas.breadit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BreaditApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialization code, but no UI-related operations
    }
}
