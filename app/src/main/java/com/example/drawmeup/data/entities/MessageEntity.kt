package com.example.drawmeup.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val conversationId: Int = 0,
    val senderId: Int = 0,
    val message: String = "",
    val date: String = ""
)
