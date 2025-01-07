package com.example.drawmeup.data.repository

import android.content.Context
import com.example.drawmeup.data.DrawMeUpRoomDB
import com.example.drawmeup.data.entities.CommentEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.CommentInterface
import com.example.drawmeup.data.models.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentRepository(val context: Context) : CommentInterface {
    private val db = DrawMeUpRoomDB.open(context)

    override suspend fun getComments(postId: Int): ArrayList<Comment> {
        return withContext(Dispatchers.IO) {
            db.comment.getComments(postId).map {
                it.toComment()
            } as ArrayList<Comment>
        }
    }

    override suspend fun addComment(comment: Comment) {
        withContext(Dispatchers.IO) {
            db.comment.addComment(comment.toEntity())
        }
    }

    override suspend fun deleteComment(comment: Comment) {
        withContext(Dispatchers.IO) {
            db.comment.deleteComment(comment.toEntity())
        }
    }

    override suspend fun testData() {
        val comments = arrayListOf(
            Comment(1, 1, 1, "This is a comment"),
            Comment(2, 1, 1, "This is another comment"),
            Comment(3, 2, 1, "This is a comment on another post")
        )
        comments.forEach {
            addComment(it)
        }
    }

}