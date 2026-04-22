package com.paybhama.app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.paybhama.app.databinding.ActivitySplashBinding
import com.paybhama.app.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check persistent states
        val prefs = getSharedPreferences("paybhama_prefs", MODE_PRIVATE)
        val onboardingCompleted = prefs.getBoolean("onboarding_completed_v2", false)
        val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            when {
                currentUser != null -> {
                    // Already logged in
                    startActivity(Intent(this, com.paybhama.app.MainActivity::class.java))
                }
                onboardingCompleted -> {
                    // Onboarding already seen, go to login
                    startActivity(Intent(this, com.paybhama.app.ui.auth.LoginActivity::class.java))
                }
                else -> {
                    // First time, show onboarding
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }
            }
            finish()
        }, 2000)
    }
}
