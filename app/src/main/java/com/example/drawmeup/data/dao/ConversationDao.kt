package com.example.drawmeup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.drawmeup.data.entities.ConversationEntity

@Dao
interface ConversationDao {

    @Insert
    suspend fun createConversation(conversation: ConversationEntity)

}