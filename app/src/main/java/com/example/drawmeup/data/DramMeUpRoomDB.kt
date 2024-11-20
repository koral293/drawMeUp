package com.example.drawmeup.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.drawmeup.data.dao.LikesDao
import com.example.drawmeup.data.dao.PostDao
import com.example.drawmeup.data.dao.UserDao
import com.example.drawmeup.data.entities.LikesEntity
import com.example.drawmeup.data.entities.PostEntity
import com.example.drawmeup.data.entities.UserEntity
import com.example.drawmeup.utils.Converters

@Database(
    entities = [UserEntity::class, PostEntity::class, LikesEntity::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class DramMeUpRoomDB : RoomDatabase() {

    abstract val user: UserDao
    abstract val post: PostDao
    abstract val likes: LikesDao

    companion object {
        fun open(context: Context): DramMeUpRoomDB {
            return Room.databaseBuilder(
                context, DramMeUpRoomDB::class.java, "draw_me_up.db"
            ).build()
        }
    }
}