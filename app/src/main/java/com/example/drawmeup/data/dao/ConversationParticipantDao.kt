package com.example.drawmeup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.drawmeup.data.entities.ConversationParticipantEntity
import com.example.drawmeup.data.models.ConversationParticipant

@Dao
interface ConversationParticipantDao {

    @Query("SELECT * FROM conversation_participant WHERE conversationId = :conversationId")
    suspend fun getParticipants(conversationId: Int) : List<ConversationParticipant>

    @Query("SELECT * FROM conversation_participant WHERE userId = :userId")
    suspend fun getConversations(userId: Int) : List<ConversationParticipant>

    @Insert
    suspend fun addParticipant(conversationParticipant: ConversationParticipantEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM conversation_participant WHERE userId IN (:user1, :user2) GROUP BY conversationId HAVING COUNT(*) = 2)")
    suspend fun conversationExists(user1: Int, user2: Int): Int

}