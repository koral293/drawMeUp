package com.example.drawmeup.data.entities

import androidx.room.Entity
import com.example.drawmeup.data.models.Likes

@Entity(tableName = "likes", primaryKeys = ["userId", "postId"], )
data class LikesEntity(
    val userId: Int,
    val postId: Int
) {

    companion object {
        fun Likes.toEntity(): LikesEntity {
            return LikesEntity(userId, postId)
        }
    }
}