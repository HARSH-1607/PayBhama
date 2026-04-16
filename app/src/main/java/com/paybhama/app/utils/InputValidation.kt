package com.paybhama.app.utils

import android.util.Patterns

object InputValidation {
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun isValidAmount(amountStr: String, currentBalance: Double): Pair<Boolean, String> {
        val amount = amountStr.toDoubleOrNull()
        return when {
            amount == null -> Pair(false, "Invalid amount format")
            amount <= 0 -> Pair(false, "Amount must be greater than zero")
            amount > currentBalance -> Pair(false, "Insufficient balance")
            else -> Pair(true, "")
        }
    }
}
