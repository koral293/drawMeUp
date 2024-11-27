package com.example.drawmeup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.drawmeup.data.entities.MessageEntity

@Dao
interface MessageDao {

    @Query("SELECT * FROM message WHERE conversationId = :conversationId ORDER BY date DESC")
    suspend fun getMessages(conversationId: Int): List<MessageEntity>

    @Insert
    suspend fun sendMessage(message: MessageEntity)

}