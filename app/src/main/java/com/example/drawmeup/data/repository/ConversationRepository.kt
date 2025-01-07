package com.example.drawmeup.data.repository

import android.content.Context
import com.example.drawmeup.data.DrawMeUpRoomDB
import com.example.drawmeup.data.entities.ConversationEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.ConversationInterface
import com.example.drawmeup.data.models.Conversation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConversationRepository(val context: Context) : ConversationInterface {
    private val db = DrawMeUpRoomDB.open(context)

    override suspend fun createConversation(conversation: Conversation): Long {
        return withContext(Dispatchers.IO) {
            db.conversation.createConversation(conversation.toEntity())
        }
    }

    override suspend fun testData() {
       withContext(Dispatchers.IO) {
           createConversation(Conversation(1))
           createConversation(Conversation(2))
           createConversation(Conversation(3))
       }
    }

}