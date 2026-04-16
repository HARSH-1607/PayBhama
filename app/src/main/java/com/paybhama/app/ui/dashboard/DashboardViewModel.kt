package com.paybhama.app.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.paybhama.app.data.model.User
import com.google.firebase.firestore.ListenerRegistration

class DashboardViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private var listener: ListenerRegistration? = null

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        listener = FirebaseFirestore.getInstance().collection("users").document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@addSnapshotListener
                }
                
                if (snapshot != null && snapshot.exists()) {
                    snapshot.toObject(User::class.java)?.let {
                        _user.value = it
                    }
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}
