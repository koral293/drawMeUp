package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.models.ConversationParticipant

interface ConversationParticipantInterface {

    suspend fun getParticipants(conversationId: Int) : List<ConversationParticipant>

    suspend fun addParticipant(conversationParticipants: ConversationParticipant)

    suspend fun removeParticipant(conversationParticipants: ConversationParticipant)

    suspend fun testData()
}