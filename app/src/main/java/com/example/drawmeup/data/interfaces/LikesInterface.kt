package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.entities.LikesEntity
import com.example.drawmeup.data.models.Likes

interface LikesInterface {

    suspend fun getAll() : List<LikesEntity>

    suspend fun getCountForPost(postId: Int) : Int

    suspend fun addLike(likes: Likes)

    suspend fun removeLike(likes: Likes)

    suspend fun testData()

}