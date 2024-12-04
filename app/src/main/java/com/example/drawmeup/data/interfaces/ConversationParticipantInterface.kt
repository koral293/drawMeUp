package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.models.ConversationParticipant

interface ConversationParticipantInterface {

    suspend fun getParticipants(conversationId: Int): List<ConversationParticipant>

    suspend fun getConversations(userId: Int): List<ConversationParticipant>

    suspend fun addParticipant(conversationParticipant: ConversationParticipant)

    suspend fun conversationExists(user1: Int, user2: Int): Int

    suspend fun testData()

}