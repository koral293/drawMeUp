package com.example.drawmeup.data.repository

import android.content.Context
import android.graphics.Bitmap
import androidx.sqlite.db.SupportSQLiteQuery
import com.bumptech.glide.Glide
import com.example.drawmeup.R
import com.example.drawmeup.data.DramMeUpRoomDB
import com.example.drawmeup.data.entities.PostEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.PostInterface
import com.example.drawmeup.data.models.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(val context: Context) : PostInterface {
    private val db = DramMeUpRoomDB.open(context)

    override suspend fun createOrUpdate(post: Post): Long {
        return withContext(Dispatchers.IO) {
            db.post.createOrUpdate(post.toEntity())
        }
    }

    override suspend fun getPostById(id: Int): Post? {
        return withContext(Dispatchers.IO) {
            db.post.getPostById(id)?.toPost()
        }
    }

    override suspend fun getAll(): List<Post> {
        return withContext(Dispatchers.IO) {
            db.post.getAll().map {
                it.toPost()
            }
        }
    }

    override suspend fun getPostsByUserId(userId: Int): List<Post> {
        return withContext(Dispatchers.IO) {
            db.post.getPostsByUserId(userId).map {
                it.toPost()
            }
        }
    }

    override suspend fun testData() {
        withContext(Dispatchers.IO) {
            fun getAsByteArray(imageId: Int): Bitmap {
                return Glide.with(context)
                    .asBitmap()
                    .load(imageId)
                    .override(256, 256)
                    .submit()
                    .get()
            }

            createOrUpdate(
                Post(
                    id = 1,
                    userId = 1,
                    name = "Example one",
                    postData = getAsByteArray(R.drawable.example_1),
                    description = "Test description",
                    tag = arrayListOf("Post", "Funny")
                )
            )
            createOrUpdate(
                Post(
                    id = 2,
                    userId = 2,
                    name = "Example two",
                    postData = getAsByteArray(R.drawable.example_2),
                    description = "Test description",
                    tag = arrayListOf("Post", "Scary")
                )
            )
            createOrUpdate(
                Post(
                    id = 3,
                    userId = 1,
                    name = "Example three",
                    postData = getAsByteArray(R.drawable.example_1),
                    description = "Test description magic",
                    tag = arrayListOf("Post", "Funny", "Scary")
                )
            )
            createOrUpdate(
                Post(
                    id = 4,
                    userId = 1,
                    name = "Example four",
                    postData = getAsByteArray(R.drawable.example_4),
                    description = "Test description",
                    tag = arrayListOf("Post", "Scary")
                )
            )
            createOrUpdate(
                Post(
                    id = 5,
                    userId = 1,
                    name = "Example one",
                    postData = getAsByteArray(R.drawable.example_1),
                    description = "Test description",
                    tag = arrayListOf("Post", "Funny")
                )
            )
            createOrUpdate(
                Post(
                    id = 6,
                    userId = 2,
                    name = "Example two",
                    postData = getAsByteArray(R.drawable.example_2),
                    description = "Test description testingMagic",
                    tag = arrayListOf("Post", "Scary")
                )
            )
            createOrUpdate(
                Post(
                    id = 7,
                    userId = 1,
                    name = "Example three",
                    postData = getAsByteArray(R.drawable.example_1),
                    description = "Test description",
                    tag = arrayListOf("Post", "Funny", "Scary")
                )
            )
            createOrUpdate(
                Post(
                    id = 8,
                    userId = 1,
                    name = "Example four",
                    postData = getAsByteArray(R.drawable.example_4),
                    description = "Test description",
                    tag = arrayListOf("Post", "Scary")
                )
            )
        }
    }

    override suspend fun searchPosts(query: SupportSQLiteQuery): List<Post> {
        return withContext(Dispatchers.IO) {
            db.post.searchPosts(query).map {
                it.toPost()
            }
        }
    }

}