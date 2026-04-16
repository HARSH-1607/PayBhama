package com.paybhama.app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.paybhama.app.databinding.ActivityOnboardingBinding
import com.paybhama.app.databinding.ItemOnboardingBinding
import com.paybhama.app.ui.auth.LoginActivity

data class OnboardingData(val title: String, val desc: String)

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private val slides = listOf(
        OnboardingData("Empowering Women", "PayBhama is designed specifically to help the women of Rajasthan achieve financial independence."),
        OnboardingData("Safe & Secure", "Your transactions are protected by state-of-the-art encryption across our secure network."),
        OnboardingData("Bahi-Khata", "Manage your daily ledgers and expenses in the traditional Indian way, but with modern simplicity.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = OnboardingAdapter(slides)
        binding.viewPager.adapter = adapter

        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem + 1 < slides.size) {
                binding.viewPager.currentItem += 1
            } else {
                finishOnboarding()
            }
        }

        binding.btnSkip.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun finishOnboarding() {
        // Save flag to skip onboarding in the future
        val prefs = getSharedPreferences("paybhama_prefs", MODE_PRIVATE)
        prefs.edit().putBoolean("onboarding_completed", true).apply()
        
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

class OnboardingAdapter(private val slides: List<OnboardingData>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(slides[position])
    }

    override fun getItemCount(): Int = slides.size

    inner class OnboardingViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(slide: OnboardingData) {
            binding.tvTitleOnboarding.text = slide.title
            binding.tvDescOnboarding.text = slide.desc
        }
    }
}
