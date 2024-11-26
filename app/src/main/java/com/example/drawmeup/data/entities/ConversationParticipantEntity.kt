package com.example.drawmeup.data.entities

import androidx.room.Entity

@Entity(tableName = "conversation_participant", primaryKeys = ["conversationId", "userId"])
data class ConversationParticipantEntity(
    val conversationId: Int,
    val userId: Int
)
