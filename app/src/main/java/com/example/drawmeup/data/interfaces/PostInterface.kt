package com.example.drawmeup.data.interfaces

import androidx.sqlite.db.SupportSQLiteQuery
import com.example.drawmeup.data.models.Post

interface PostInterface {

    suspend fun createOrUpdate(post: Post): Long

    suspend fun getPostById(id: Int): Post?

    suspend fun getAll(): List<Post>

    suspend fun getPostsByUserId(userId: Int): List<Post>

    suspend fun testData()

    suspend fun searchPosts(query: SupportSQLiteQuery): List<Post>

}
