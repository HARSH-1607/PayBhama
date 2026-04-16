package com.paybhama.app.data.model

data class Transaction(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val amount: Double = 0.0,
    val timestamp: Long = 0L,
    val status: String = "PENDING" // PENDING, COMPLETED, FAILED
)
