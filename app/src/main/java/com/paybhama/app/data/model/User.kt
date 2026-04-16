package com.paybhama.app.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val balance: Double = 0.0,
    val fcmToken: String = ""
)
