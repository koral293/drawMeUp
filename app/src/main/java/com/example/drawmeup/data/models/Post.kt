package com.example.drawmeup.data.models

import android.graphics.Bitmap

data class Post(
    val id: Int,
    val userId: Int,
    val name: String,
    val postData: Bitmap,
    val description: String,
    //val comments: ArrayList<Comment>,
    val tag: ArrayList<String>
)