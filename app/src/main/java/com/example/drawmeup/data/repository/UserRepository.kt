package com.example.drawmeup.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.example.drawmeup.R
import com.example.drawmeup.data.DrawMeUpRoomDB
import com.example.drawmeup.data.entities.UserEntity
import com.example.drawmeup.data.entities.UserEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.UserInterface
import com.example.drawmeup.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(val context: Context) : UserInterface {
    private val db = DrawMeUpRoomDB.open(context)

    override suspend fun createOrUpdate(user: User) {
        withContext(Dispatchers.IO) {
            db.user.createOrUpdate(user.toEntity())
        }
    }

    override suspend fun getAll(): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            db.user.getAll()
        }
    }

    override suspend fun getById(id: Int): UserEntity {
        return withContext(Dispatchers.IO) {
            db.user.getById(id)
        }
    }

    override suspend fun getByEmailAndPassword(email: String, password: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            db.user.getByEmailAndPassword(email, password)
        }
    }

    override suspend fun getByName(name: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            db.user.getByName(name)
        }
    }

    override suspend fun getByEmail(email: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            db.user.getByEmail(email)
        }
    }

    override suspend fun testData() {
        withContext(Dispatchers.IO) {
            if (getAll().isEmpty()) {

                fun getAsByteArray(imageId: Int): Bitmap {
                    return Glide.with(context)
                        .asBitmap()
                        .load(imageId)
                        .override(256, 256)
                        .submit()
                        .get()
                }

                createOrUpdate(
                    User(
                        id = 1,
                        name = "Test",
                        email = "test@test",
                        password = "test",
                        avatar = getAsByteArray(R.drawable.example_1)
                    )
                )
                createOrUpdate(
                    User(
                        id = 2,
                        name = "Test2",
                        email = "test@gmail.com",
                        password = "test",
                        avatar = getAsByteArray(R.drawable.example_3)
                    )
                )
                createOrUpdate(
                    User(
                        id = 3,
                        name = "conversationTest",
                        email = "conversationTest@gmail.com",
                        password = "conversationTest",
                        avatar = getAsByteArray(R.drawable.example_4)
                    )
                )
            }
        }
    }
}