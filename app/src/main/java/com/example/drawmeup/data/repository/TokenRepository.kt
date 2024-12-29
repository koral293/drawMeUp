package com.example.drawmeup.data.repository

import android.content.Context
import com.example.drawmeup.data.DramMeUpRoomDB
import com.example.drawmeup.data.entities.TokenEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.TokenInterface
import com.example.drawmeup.data.models.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenRepository (val context: Context) : TokenInterface {
    private val db = DramMeUpRoomDB.open(context)

    override suspend fun createOrUpdate(token: Token): Long {
        return withContext(Dispatchers.IO) {
            db.token.createOrUpdate(token.toEntity())
        }
    }

}