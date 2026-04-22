package com.paybhama.app.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paybhama.app.databinding.FragmentBillPaymentBinding

class BillPaymentFragment : Fragment() {

    private var _binding: FragmentBillPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBillPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        
        // Add click listeners for specific bills if needed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
