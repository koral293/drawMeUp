package com.example.drawmeup.data.repository

import android.content.Context
import com.example.drawmeup.data.DramMeUpRoomDB
import com.example.drawmeup.data.entities.LikesEntity
import com.example.drawmeup.data.entities.LikesEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.LikesInterface
import com.example.drawmeup.data.models.Likes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LikesRepository(val context: Context) : LikesInterface {
    private val db = DramMeUpRoomDB.open(context)

    override suspend fun getAll(): List<LikesEntity> {
        return withContext(Dispatchers.IO) {
            db.likes.getAll()
        }
    }

    override suspend fun getCountForPost(postId: Int): Int {
        return withContext(Dispatchers.IO) {
            db.likes.getCountForPost(postId)
        }
    }

    override suspend fun addLike(likes: Likes) {
        withContext(Dispatchers.IO) {
            db.likes.addLike(likes.toEntity())
        }
    }

    override suspend fun removeLike(likes: Likes) {
        withContext(Dispatchers.IO) {
            db.likes.removeLike(likes.toEntity())
        }
    }

    override suspend fun testData() {
        withContext(Dispatchers.IO) {
            if (getAll().isEmpty()) {
                addLike(Likes(2, 4))
                addLike(Likes(2, 1))
                addLike(Likes(2, 2))
                addLike(Likes(2, 3))
                addLike(Likes(3, 4))
                addLike(Likes(3, 1))
                addLike(Likes(3, 2))
                addLike(Likes(3, 3))
                addLike(Likes(4, 4))
                addLike(Likes(4, 1))
                addLike(Likes(4, 2))
                addLike(Likes(4, 3))
                addLike(Likes(5, 4))
                addLike(Likes(5, 1))
                addLike(Likes(5, 2))
                addLike(Likes(5, 3))
                addLike(Likes(6, 3))
                addLike(Likes(7, 3))
                addLike(Likes(8, 3))
            }
        }
    }
}