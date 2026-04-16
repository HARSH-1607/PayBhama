package com.paybhama.app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paybhama.app.R
import com.paybhama.app.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvBalance.text = "₹${user.balance}"
            binding.tvWelcome.text = "Namaste, ${user.name}"
        }

        binding.btnSendMoney.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_sendMoney)
        }
        
        binding.btnHistory.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }

        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_profile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
