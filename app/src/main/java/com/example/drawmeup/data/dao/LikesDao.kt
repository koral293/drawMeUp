package com.example.drawmeup.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.drawmeup.data.entities.LikesEntity

@Dao
interface LikesDao {

    @Query("SELECT * FROM likes")
    suspend fun getAll(): List<LikesEntity>

    @Query("SELECT COUNT(*) FROM likes WHERE postId = :postId")
    suspend fun getCountForPost(postId: Int) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLike(likesEntity: LikesEntity)

    @Query("SELECT * FROM likes WHERE userId = :userId AND postId = :postId")
    suspend fun getLike(userId: Int, postId: Int) : LikesEntity?

    @Delete
    suspend fun removeLike(likesEntity: LikesEntity)
}