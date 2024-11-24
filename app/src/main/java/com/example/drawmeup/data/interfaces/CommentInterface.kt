package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.models.Comment

interface CommentInterface {

    suspend fun getComments(postId: Int): ArrayList<Comment>

    suspend fun addComment(comment: Comment)

    suspend fun deleteComment(comment: Comment)

    suspend fun testData()
}