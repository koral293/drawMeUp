package com.example.drawmeup.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.drawmeup.data.models.User

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String
) {
    fun toUser(): User {
        return User(id, name, email, password)
    }

    companion object {
        fun User.toEntity(): UserEntity {
            return UserEntity(id, name, email, password)
        }
    }
}

