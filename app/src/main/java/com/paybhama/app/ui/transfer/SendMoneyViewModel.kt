package com.paybhama.app.ui.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.paybhama.app.data.model.Transaction
import com.paybhama.app.data.repository.TransactionRepository
import kotlinx.coroutines.launch

class SendMoneyViewModel : ViewModel() {
    private val repository = TransactionRepository()

    private val _transactionState = MutableLiveData<Result<Transaction>>()
    val transactionState: LiveData<Result<Transaction>> = _transactionState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _history = MutableLiveData<List<Transaction>>()
    val history: LiveData<List<Transaction>> = _history

    fun sendMoney(receiverEmail: String, amount: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            val senderId = FirebaseAuth.getInstance().currentUser?.uid
            if (senderId != null) {
                val result = repository.sendMoney(senderId, receiverEmail, amount)
                _transactionState.value = result
            } else {
                _transactionState.value = Result.failure(Exception("User not authenticated"))
            }
            _isLoading.value = false
        }
    }

    fun loadTransactionHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {
                val result = repository.getTransactionHistory(userId)
                if (result.isSuccess) {
                    _history.value = result.getOrNull() ?: emptyList()
                }
            }
            _isLoading.value = false
        }
    }
}
