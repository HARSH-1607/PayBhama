package com.paybhama.app.ui.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paybhama.app.databinding.FragmentSendMoneyBinding

class SendMoneyFragment : Fragment() {

    private var _binding: FragmentSendMoneyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SendMoneyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.transactionState.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                Toast.makeText(context, "Transfer Successful!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(context, "Transfer failed: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSend.setOnClickListener {
            val email = binding.etReceiverEmail.text.toString()
            val amountStr = binding.etAmount.text.toString()
            val amount = amountStr.toDoubleOrNull() ?: 0.0

            if (email.isNotEmpty() && amount > 0) {
                viewModel.sendMoney(email, amount)
            } else {
                Toast.makeText(context, "Please enter valid details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
