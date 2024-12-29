package com.example.drawmeup.utils

import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.dictionary.TokenType
import com.example.drawmeup.data.models.Token
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForgotPasswordTokenGenerator {
    private val tokenRepository = RepositoryLocator.tokenRepository


    private fun generateToken(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..20)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun generate(baseUri: String): String {
        val token = generateToken()
        val type = TokenType.FORGOT_PASSWORD.getString()
        val expiresAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .format(LocalDateTime.now().plusMinutes(30)).toString()
        runBlocking {
            tokenRepository.createOrUpdate(Token(0, token, type, expiresAt))
        }
        return "$baseUri/forgot-password?token=${generateToken()}"
    }

}