package com.paybhama.app

import android.app.Application
import com.google.firebase.FirebaseApp

class PayBhamaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}
