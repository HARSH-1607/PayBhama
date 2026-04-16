package com.paybhama.app.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paybhama.app.data.model.Transaction
import com.paybhama.app.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter(private val currentUserId: String) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions: List<Transaction> = emptyList()

    fun submitList(newList: List<Transaction>) {
        transactions = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            val isSender = transaction.senderId == currentUserId
            
            if (isSender) {
                binding.tvTransactionInfo.text = "Sent to ${transaction.receiverId}" // Ideally show Name/Email
                binding.tvTransactionAmount.text = "-₹${String.format("%.2f", transaction.amount)}"
                binding.tvTransactionAmount.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.holo_red_dark))
                binding.ivTransactionType.setImageResource(android.R.drawable.ic_menu_send)
            } else {
                binding.tvTransactionInfo.text = "Received from ${transaction.senderId}"
                binding.tvTransactionAmount.text = "+₹${String.format("%.2f", transaction.amount)}"
                binding.tvTransactionAmount.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.holo_green_dark))
                binding.ivTransactionType.setImageResource(android.R.drawable.ic_menu_edit) // Placeholder for 'receive' icon
            }

            val sdf = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
            binding.tvTransactionDate.text = sdf.format(Date(transaction.timestamp))
        }
    }
}
