package com.example.drawmeup.data.entities

import androidx.room.Entity
import com.example.drawmeup.data.models.ConversationParticipant

@Entity(tableName = "conversation_participant", primaryKeys = ["conversationId", "userId"])
data class ConversationParticipantEntity(
    val conversationId: Int,
    val userId: Int
) {

    companion object {
        fun ConversationParticipant.toEntity(): ConversationParticipantEntity {
            return ConversationParticipantEntity(conversationId, userId)
        }
    }

}
