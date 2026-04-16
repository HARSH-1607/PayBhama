package com.paybhama.app.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.paybhama.app.databinding.FragmentHistoryBinding
import com.paybhama.app.ui.transfer.SendMoneyViewModel

class TransactionHistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SendMoneyViewModel by viewModels()
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        adapter = TransactionAdapter(currentUserId)
        binding.rvTransactions.adapter = adapter

        viewModel.history.observe(viewLifecycleOwner) { transactions ->
            if (transactions.isEmpty()) {
                binding.tvNoTransactions.visibility = View.VISIBLE
                binding.rvTransactions.visibility = View.GONE
                binding.tvExpensesAmount.text = "$0.00"
            } else {
                binding.tvNoTransactions.visibility = View.GONE
                binding.rvTransactions.visibility = View.VISIBLE
                adapter.submitList(transactions)
                
                // Calculate total expenses for the header
                val total = transactions.filter { it.senderId == currentUserId }.sumOf { it.amount }
                binding.tvExpensesAmount.text = "₹${String.format("%.2f", total)}"
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.loadTransactionHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
