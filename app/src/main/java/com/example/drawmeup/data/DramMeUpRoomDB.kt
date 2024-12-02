package com.example.drawmeup.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.drawmeup.data.dao.CommentDao
import com.example.drawmeup.data.dao.ConversationDao
import com.example.drawmeup.data.dao.ConversationParticipantDao
import com.example.drawmeup.data.dao.LikesDao
import com.example.drawmeup.data.dao.MessageDao
import com.example.drawmeup.data.dao.PostDao
import com.example.drawmeup.data.dao.UserDao
import com.example.drawmeup.data.entities.CommentEntity
import com.example.drawmeup.data.entities.ConversationEntity
import com.example.drawmeup.data.entities.ConversationParticipantEntity
import com.example.drawmeup.data.entities.LikesEntity
import com.example.drawmeup.data.entities.MessageEntity
import com.example.drawmeup.data.entities.PostEntity
import com.example.drawmeup.data.entities.UserEntity
import com.example.drawmeup.utils.Converters

@Database(
    entities = [UserEntity::class, PostEntity::class, LikesEntity::class, CommentEntity::class,
        ConversationEntity::class, MessageEntity::class, ConversationParticipantEntity::class],
    version = 8
)
@TypeConverters(Converters::class)
abstract class DramMeUpRoomDB : RoomDatabase() {

    abstract val user: UserDao
    abstract val post: PostDao
    abstract val likes: LikesDao
    abstract val comment: CommentDao
    abstract val conversation: ConversationDao
    abstract val conversationParticipant: ConversationParticipantDao
    abstract val message: MessageDao

    companion object {
        fun open(context: Context): DramMeUpRoomDB {
            return Room.databaseBuilder(
                context, DramMeUpRoomDB::class.java, "draw_me_up.db"
            ).build()
        }
    }
}