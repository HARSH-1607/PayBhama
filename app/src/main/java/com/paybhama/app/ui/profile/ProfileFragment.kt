package com.paybhama.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paybhama.app.data.repository.AuthRepository
import com.paybhama.app.databinding.FragmentProfileBinding
import com.paybhama.app.ui.auth.LoginActivity
import com.paybhama.app.ui.dashboard.DashboardViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private val authRepository = AuthRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvProfileName.text = user.name
            binding.tvProfileEmail.text = user.email
        }

        binding.btnIntro.setOnClickListener {
            val intent = Intent(requireContext(), com.paybhama.app.ui.onboarding.OnboardingActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            authRepository.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
