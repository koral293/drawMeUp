package com.example.drawmeup.data.repository

import android.content.Context
import com.example.drawmeup.data.DramMeUpRoomDB
import com.example.drawmeup.data.entities.MessageEntity.Companion.toEntity
import com.example.drawmeup.data.interfaces.MessageInterface
import com.example.drawmeup.data.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageRepository(val context: Context) : MessageInterface {
    private val db = DramMeUpRoomDB.open(context)
    private val conversationParticipantRepository = ConversationParticipantRepository(context)

    override suspend fun getMessages(conversationId: Int): List<Message> {
        return withContext(Dispatchers.IO) {
            db.message.getMessages(conversationId).map {
                it.toMessage()
            } as ArrayList<Message>
        }
    }

    override suspend fun sendMessage(message: Message) {
        withContext(Dispatchers.IO) {
            db.message.sendMessage(message.toEntity())

            if (message.message == "Test message") {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val dateNow = LocalDateTime.now()
                conversationParticipantRepository.getParticipants(message.conversationId).forEach {
                    if (it.userId != message.senderId) {
                        val message = Message(
                            0,
                            message.conversationId,
                            it.userId,
                            "Callback message",
                            formatter.format(dateNow)
                        )
                        db.message.sendMessage(message.toEntity())
                    }
                }
            }
        }
    }

    override suspend fun testData() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateNow = LocalDateTime.now()
        sendMessage(Message(1, 1, 1, "Hello", formatter.format(dateNow.minusMinutes(4))))
        sendMessage(Message(2, 1, 2, "Hello! How are you?", formatter.format(dateNow.minusMinutes(3))))
        sendMessage(Message(3, 1, 1, "I am good thanks ^^", formatter.format(dateNow.minusMinutes(1))))
        sendMessage(Message(4, 2, 1, "De", formatter.format(dateNow.minusSeconds(12))))
        sendMessage(Message(5, 2, 1, "bil", formatter.format(dateNow)))
        sendMessage(Message(6, 3, 2, "Tutututu", formatter.format(dateNow.minusSeconds(2))))
        sendMessage(Message(7, 3, 2, "Max Vesrstappen", formatter.format(dateNow)))
    }

}