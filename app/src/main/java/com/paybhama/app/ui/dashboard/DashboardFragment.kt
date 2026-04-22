package com.paybhama.app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            binding.tvBalance.text = "₹${"%,.2f".format(user.balance)}"
            binding.tvWelcome.text = "Namaste, ${user.name}"
        }

        // Quick Actions
        binding.btnSend.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_sendMoney)
        }
        
        binding.btnHistoryAction.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }

        binding.btnBills?.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_billPayment)
        }

        binding.btnRewards?.setOnClickListener {
            Toast.makeText(requireContext(), "Bhama Rewards coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.btnGridAddMoney.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_addMoney)
        }

        binding.btnGridScan.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_scanPay)
        }

        // Top Bar
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.btnNotifications.setOnClickListener {
            Toast.makeText(requireContext(), "No new notifications", Toast.LENGTH_SHORT).show()
        }

        // Fab Scanner
        binding.fabScanner.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_scanPay)
        }
        
        binding.btnAddMoney.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_addMoney)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

