package com.example.drawmeup.data.models

import android.graphics.Bitmap

data class Post(
    var id: Int,
    val userId: Int,
    val name: String,
    val postData: Bitmap,
    val description: String,
    val tag: ArrayList<String>
)