package com.example.drawmeup.data.repository

import android.content.Context
import com.example.drawmeup.data.DramMeUpRoomDB
import com.example.drawmeup.data.entities.UserEntity
import com.example.drawmeup.data.entities.UserEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.UserInterface
import com.example.drawmeup.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository (val context: Context) : UserInterface {
    val db = DramMeUpRoomDB.open(context)
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
                createOrUpdate(
                    User(
                        id = 1,
                        name = "Test",
                        email = "test@gmail.com",
                        password = "test",
                    ))
                createOrUpdate(
                    User(
                        id = 2,
                        name = "Test2",
                        email = "test@gmail.com",
                        password = "test",
                    ))
            }
    }
    }
}