package com.paybhama.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.paybhama.app.data.model.Transaction
import kotlinx.coroutines.tasks.await
import java.util.UUID

class TransactionRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun sendMoney(senderId: String, receiverEmail: String, amount: Double): Result<Transaction> {
        return try {
            if (amount <= 0) throw Exception("Amount must be greater than zero")

            val normalizedReceiverEmail = receiverEmail.trim().lowercase()

            // Find receiver by email
            val receiverQuery = db.collection("users")
                .whereEqualTo("email", normalizedReceiverEmail)
                .get()
                .await()

            if (receiverQuery.isEmpty) {
                throw Exception("Receiver with email $normalizedReceiverEmail not found. Please ensure the user has registered.")
            }
            
            val receiverDoc = receiverQuery.documents.first()
            val receiverId = receiverDoc.id

            if (senderId == receiverId) {
                throw Exception("You cannot send money to yourself")
            }

            val transactionId = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis()
            
            val transaction = Transaction(
                id = transactionId,
                senderId = senderId,
                receiverId = receiverId,
                amount = amount,
                timestamp = timestamp,
                status = "COMPLETED"
            )

            // Firestore Transaction to ensure consistency
            db.runTransaction { firestoreTransaction ->
                val senderRef = db.collection("users").document(senderId)
                val receiverRef = db.collection("users").document(receiverId)
                
                val snapshotSender = firestoreTransaction.get(senderRef)
                if (!snapshotSender.exists()) throw Exception("Sender account does not exist in database")
                
                val senderBalance = snapshotSender.getDouble("balance") ?: 0.0
                if (senderBalance < amount) throw Exception("Insufficient balance. Current: $senderBalance")
                
                val snapshotReceiver = firestoreTransaction.get(receiverRef)
                if (!snapshotReceiver.exists()) throw Exception("Receiver account does not exist in database")
                
                val receiverBalance = snapshotReceiver.getDouble("balance") ?: 0.0

                firestoreTransaction.update(senderRef, "balance", senderBalance - amount)
                firestoreTransaction.update(receiverRef, "balance", receiverBalance + amount)
                
                val transactionRef = db.collection("transactions").document(transactionId)
                firestoreTransaction.set(transactionRef, transaction)
            }.await()

            Result.success(transaction)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getTransactionHistory(userId: String): Result<List<Transaction>> {
        return try {
            // Get sent
            val sentQuery = db.collection("transactions")
                .whereEqualTo("senderId", userId)
                .get()
                .await()
                
            // Get received
            val receivedQuery = db.collection("transactions")
                .whereEqualTo("receiverId", userId)
                .get()
                .await()
                
            val allTransactions = mutableListOf<Transaction>()
            allTransactions.addAll(sentQuery.toObjects(Transaction::class.java))
            allTransactions.addAll(receivedQuery.toObjects(Transaction::class.java))
            
            // Sort by timestamp descending
            allTransactions.sortByDescending { it.timestamp }
            
            Result.success(allTransactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
