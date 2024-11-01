package com.example.drawmeup.data.entities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.utils.Converters
import java.io.ByteArrayOutputStream

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
        val bitmap = BitmapFactory.decodeByteArray(postData, 0, postData.size)
        return Post(id, userId, name, bitmap, description, tag)
    }

    companion object {
        fun Post.toEntity(): PostEntity {
            val outputStream = ByteArrayOutputStream()
            postData.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return PostEntity(id, userId, name, outputStream.toByteArray(), description, tag)
        }
    }
}