package com.example.drawmeup.data.repository

import android.content.Context
import com.example.drawmeup.data.DramMeUpRoomDB
import com.example.drawmeup.data.entities.PostEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.PostInterface
import com.example.drawmeup.data.models.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(val context: Context) : PostInterface {
    private val db = DramMeUpRoomDB.open(context)

    override suspend fun createOrUpdate(post: Post) {
        withContext(Dispatchers.IO) {
            db.post.createOrUpdate(post.toEntity())
        }
    }
}