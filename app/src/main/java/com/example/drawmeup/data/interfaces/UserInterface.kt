package com.example.drawmeup.data.interfaces

import com.example.drawmeup.data.entities.UserEntity
import com.example.drawmeup.data.models.User

interface UserInterface {

    suspend fun createOrUpdate(user: User)

    suspend fun getAll(): List<UserEntity>

    suspend fun getById(id: Int): UserEntity

    suspend fun getByEmailAndPassword(email: String, password: String): UserEntity?

    suspend fun getByName(name: String): UserEntity?

    suspend fun getByEmail(email: String): UserEntity?

    suspend fun testData()

}