package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.models.Token

interface TokenInterface {

    suspend fun createOrUpdate(token: Token): Long

}