package com.example.drawmeup.data.models

data class Message(
    val id: Int = 0,
    val conversationId: Int = 0,
    val senderId: Int = 0,
    val message: String = "",
    val date: String = ""
)
