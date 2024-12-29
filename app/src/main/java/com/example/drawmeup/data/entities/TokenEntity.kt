package com.example.drawmeup.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.drawmeup.data.models.Token

@Entity(tableName = "token")
data class TokenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val token: String,
    val tokenType: String,
    val expiresAt: String
) {

    companion object {
        fun Token.toEntity(): TokenEntity {
            return TokenEntity(id, token, tokenType, expiresAt)
        }
    }
}