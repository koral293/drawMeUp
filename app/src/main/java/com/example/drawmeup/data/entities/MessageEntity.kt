package com.example.drawmeup.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.drawmeup.data.models.Message

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val conversationId: Int = 0,
    val senderId: Int = 0,
    val message: String = "",
    val date: String = ""
) {
    fun toMessage(): Message {
        return Message(id, conversationId, senderId, message, date)
    }

    companion object {
        fun Message.toEntity(): MessageEntity {
            return MessageEntity(id, conversationId, senderId, message, date)
        }
    }
}
