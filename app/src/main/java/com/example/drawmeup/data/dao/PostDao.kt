package com.example.drawmeup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.drawmeup.data.entities.PostEntity

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(post: PostEntity) : Long

    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity?
}