package com.example.drawmeup.data.repository

import android.content.Context
import com.example.drawmeup.data.DramMeUpRoomDB
import com.example.drawmeup.data.entities.ConversationParticipantEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.ConversationParticipantInterface
import com.example.drawmeup.data.models.ConversationParticipant
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConversationParticipantRepository(val context: Context) : ConversationParticipantInterface {
    private val db = DramMeUpRoomDB.open(context)

    override suspend fun getParticipants(conversationId: Int): List<ConversationParticipant> {
        return withContext(Dispatchers.IO) {
            val data = db.conversationParticipant.getParticipants(conversationId)
            Logger.debug(data.toString())
            data
        }
    }

    override suspend fun getConversations(userId: Int): List<ConversationParticipant> {
        return withContext(Dispatchers.IO) {
            db.conversationParticipant.getConversations(userId)
        }
    }

    override suspend fun addParticipant(conversationParticipant: ConversationParticipant) {
        withContext(Dispatchers.IO) {
            db.conversationParticipant.addParticipant(conversationParticipant.toEntity())
        }
    }

    override suspend fun conversationExists(user1: Int, user2: Int): Int {
        return withContext(Dispatchers.IO) {
            db.conversationParticipant.conversationExists(user1, user2)
        }
    }

    override suspend fun testData() {
        addParticipant(ConversationParticipant(1, 1))
        addParticipant(ConversationParticipant(1, 2))
        addParticipant(ConversationParticipant(2, 1))
        addParticipant(ConversationParticipant(2, 2))
        addParticipant(ConversationParticipant(3, 1))
        addParticipant(ConversationParticipant(3, 2))
    }
}