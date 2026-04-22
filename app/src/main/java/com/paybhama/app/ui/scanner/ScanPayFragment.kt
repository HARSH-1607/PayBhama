package com.paybhama.app.ui.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paybhama.app.databinding.FragmentScanPayBinding

class ScanPayFragment : Fragment() {

    private var _binding: FragmentScanPayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnMockScan.setOnClickListener {
            // Simulate scanning a merchant
            Toast.makeText(requireContext(), "Merchant Identified: Bhama Groceries", Toast.LENGTH_SHORT).show()
            // In a real app, this would navigate to a payment amount screen for the merchant
            Toast.makeText(requireContext(), "Payment Successful!", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
