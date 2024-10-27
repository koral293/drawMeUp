package com.example.drawmeup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.drawmeup.data.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(user: UserEntity)

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getById(id: Int): UserEntity

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getByEmailAndPassword(email: String, password: String): UserEntity?
}