package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.models.Message

interface MessageInterface {

    suspend fun getMessages(conversationId: Int) : List<Message>

    suspend fun sendMessage(Message: Message)

    suspend fun testData()
}