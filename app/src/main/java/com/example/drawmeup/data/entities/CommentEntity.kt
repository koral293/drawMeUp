package com.example.drawmeup.data.entities

import androidx.room.Entity
import com.example.drawmeup.data.models.Comment

@Entity(tableName = "comments", primaryKeys = ["id"])
data class CommentEntity (
    val id: Int = 0,
    val userId: Int = 0,
    val postId: Int = 0,
    val comment: String = "",
    val date: String = ""
) {
    fun toComment(): Comment {
        return Comment(id, userId, postId, comment, date)
    }

    companion object {
        fun Comment.toEntity(): CommentEntity {
            return CommentEntity(id, userId, postId, comment, date)
        }
    }
}