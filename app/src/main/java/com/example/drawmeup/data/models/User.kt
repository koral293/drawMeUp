package com.example.drawmeup.data.models

import android.graphics.Bitmap

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    var avatar: Bitmap
)
