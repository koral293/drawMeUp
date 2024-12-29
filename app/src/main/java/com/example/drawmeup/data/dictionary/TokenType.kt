package com.example.drawmeup.data.dictionary

enum class TokenType {

    FORGOT_PASSWORD;

    fun getString(): String {
        return when (this) {
            FORGOT_PASSWORD -> "forgot_password"
        }
    }

}