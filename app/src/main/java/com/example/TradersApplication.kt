package com.example

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class TradersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        val app = FirebaseApp.initializeApp(this)
        if (app == null) {
            val options = FirebaseOptions.Builder()
                .setProjectId("dummy-project-id")
                .setApplicationId("1:123456789012:android:1234567890abcdef")
                .setApiKey("dummy-api-key-which-is-long-enough-for-firebase")
                .build()
            FirebaseApp.initializeApp(this, options)
        }
    }
}
