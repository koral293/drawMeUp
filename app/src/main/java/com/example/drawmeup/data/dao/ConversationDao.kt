package com.example.drawmeup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.drawmeup.data.entities.ConversationEntity

@Dao
interface ConversationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createConversation(conversation: ConversationEntity): Long

}