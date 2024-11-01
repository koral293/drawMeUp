package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.models.Post

interface PostInterface {

    suspend fun createOrUpdate(post: Post) : Long

    suspend fun getPostById(id: Int): Post?

    companion object {
        const val GENERATE_ID = 0
    }
}
