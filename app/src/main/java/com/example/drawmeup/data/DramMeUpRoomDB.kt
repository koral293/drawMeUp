package com.example.drawmeup.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drawmeup.data.dao.UserDao
import com.example.drawmeup.data.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class DramMeUpRoomDB : RoomDatabase() {

    abstract val user: UserDao

    companion object {
        fun open(context: Context): DramMeUpRoomDB {
            return Room.databaseBuilder(
                context, DramMeUpRoomDB::class.java, "draw_me_up.db"
            ).build()
        }
    }
}