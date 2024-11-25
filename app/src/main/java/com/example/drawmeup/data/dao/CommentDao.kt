package com.example.drawmeup.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.drawmeup.data.entities.CommentEntity

@Dao
interface CommentDao {

    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY date DESC")
    suspend fun getComments(postId: Int): List<CommentEntity>

    @Insert
    suspend fun addComment(comment: CommentEntity)

    @Delete
    suspend fun deleteComment(comment: CommentEntity)

}