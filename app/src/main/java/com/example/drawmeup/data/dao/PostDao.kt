package com.example.drawmeup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.drawmeup.data.entities.PostEntity

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(post: PostEntity): Long

    @Query("SELECT * FROM post")
    suspend fun getAll(): List<PostEntity>

    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity?

    @Query("SELECT * FROM post WHERE userId = :userId")
    suspend fun getPostsByUserId(userId: Int): List<PostEntity>

    @RawQuery
    suspend fun searchPosts(query: SupportSQLiteQuery): List<PostEntity>

}