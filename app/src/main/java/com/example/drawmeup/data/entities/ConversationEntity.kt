package com.example.drawmeup.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.drawmeup.data.models.Conversation

@Entity(tableName = "conversation")
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    companion object {
        fun Conversation.toEntity(): ConversationEntity {
            return ConversationEntity(id)
        }
    }
}
