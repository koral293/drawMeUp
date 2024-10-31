package com.example.drawmeup.data.models

data class Post(
    val id: Int,
    val userId: Int,
    val name: String,
    val postData: ByteArray,
    val description: String,
    //val comments: ArrayList<Comment>,
    val tag: ArrayList<String>
)