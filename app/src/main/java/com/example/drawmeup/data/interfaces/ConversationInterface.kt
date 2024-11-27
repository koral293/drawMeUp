package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.models.Conversation

interface ConversationInterface {

    suspend fun createConversation(conversation: Conversation)

    suspend fun testData()
}