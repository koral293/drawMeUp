package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.models.Post

interface PostInterface {

    suspend fun createOrUpdate(post: Post): Long

    suspend fun getPostById(id: Int): Post?

    suspend fun getAll(): List<Post>

    suspend fun testData()

    companion object {
        const val GENERATE_ID = 0
    }
}
