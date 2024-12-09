package com.example.drawmeup.data.entities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.drawmeup.data.models.User
import java.io.ByteArrayOutputStream

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val avatar: ByteArray
) {
    fun toUser(): User {
        val bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.size)
        return User(id, name, email, password, bitmap)
    }

    companion object {
        fun User.toEntity(): UserEntity {
            val outputStream = ByteArrayOutputStream()
            avatar.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return UserEntity(id, name, email, password, outputStream.toByteArray())
        }
    }
}

