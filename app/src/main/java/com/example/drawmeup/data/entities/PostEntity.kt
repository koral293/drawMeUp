package com.example.drawmeup.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.utils.Converters

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: Int,
    val name: String,
    val postData: ByteArray,
    val description: String,
    //val comments: ArrayList<Comment>,
    @TypeConverters(Converters::class)
    val tag: ArrayList<String>
) {
    fun toPost(): Post {
        return Post(id, userId, name, postData, description, tag)
    }

    companion object {
        fun Post.toEntity(): PostEntity {
            return PostEntity(id, userId, name, postData, description, tag)
        }
    }
}