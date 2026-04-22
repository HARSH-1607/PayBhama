package com.paybhama.app.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.paybhama.app.data.repository.TransactionRepository
import com.paybhama.app.databinding.FragmentAddMoneyBinding
import kotlinx.coroutines.launch

class AddMoneyFragment : Fragment() {

    private var _binding: FragmentAddMoneyBinding? = null
    private val binding get() = _binding!!
    private val repository = TransactionRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chip100.setOnClickListener { binding.etAmount.setText("100") }
        binding.chip500.setOnClickListener { binding.etAmount.setText("500") }
        binding.chip2000.setOnClickListener { binding.etAmount.setText("2000") }

        binding.btnProceed.setOnClickListener {
            val amountStr = binding.etAmount.text.toString()
            val amount = amountStr.toDoubleOrNull()

            if (amount == null || amount <= 0) {
                Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener

            binding.btnProceed.isEnabled = false
            lifecycleScope.launch {
                val result = repository.addMoney(userId, amount)
                if (result.isSuccess) {
                    Toast.makeText(requireContext(), "₹$amount added to wallet!", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                } else {
                    binding.btnProceed.isEnabled = true
                    Toast.makeText(requireContext(), "Failed to add money: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
