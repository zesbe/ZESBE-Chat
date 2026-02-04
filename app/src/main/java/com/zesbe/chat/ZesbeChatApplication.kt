package com.zesbe.chat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * ZESBE Chat Application
 *
 * Entry point for the application.
 * Initializes Hilt dependency injection.
 */
@HiltAndroidApp
class ZesbeChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide components here
    }
}
