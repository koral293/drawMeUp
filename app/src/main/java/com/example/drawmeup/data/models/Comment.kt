package com.example.drawmeup.data.models

data class Comment (
    val id: Int = 0,
    val userId: Int = 0,
    val postId: Int = 0,
    val comment: String = "",
    val date: String = ""
)