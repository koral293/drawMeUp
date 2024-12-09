package com.example.drawmeup.ui.chat

import UserSession
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Message
import com.example.drawmeup.data.models.User
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatViewModel : ViewModel() {

    val chatList: MutableLiveData<List<Message>> = MutableLiveData(emptyList())
    val user: MutableLiveData<User> = MutableLiveData(User(0, "", "", "", Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)))
    val message = MutableLiveData("")
    var converastionId: Int = 0

    private val conversationParticipantRepository =
        RepositoryLocator.conversationParticipantRepository
    private val messageRepository = RepositoryLocator.messageRepository
    private val userRepository = RepositoryLocator.userRepository

    fun initChat(id: Int) {
        converastionId = id
        viewModelScope.launch {
            val otherUserConversationParticipant =
                conversationParticipantRepository.getParticipants(id).first {
                    it.userId != UserSession.user.id
                }
            user.value = userRepository.getById(otherUserConversationParticipant.userId).toUser()
            chatList.value = messageRepository.getMessages(id).reversed()
            refreshChat()
        }
    }

    private fun refreshChat() {
        viewModelScope.launch {
            while (true) {
                val refreshedMessages = messageRepository.getMessages(converastionId).reversed()
                if (chatList.value != refreshedMessages) {
                    Logger.debug("New messages found - refreshing!")
                    chatList.value = refreshedMessages
                }
                Logger.debug("Delaying for 5 seconds...")
                delay(5 * 1000)
            }
        }
    }

    private fun loadMessages() {
        viewModelScope.launch {
            chatList.value = messageRepository.getMessages(converastionId).reversed()
        }
    }

    fun sendMessage(): ActionStatus {
        if (message.value.toString().isNotEmpty()) {
            runBlocking {
                messageRepository.sendMessage(
                    Message(
                        0,
                        converastionId,
                        UserSession.user.id,
                        message.value.toString(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            .format(LocalDateTime.now()).toString()
                    )
                )
                loadMessages()
            }
            message.value = ""
            return ActionStatus.SUCCESS
        }
        return ActionStatus.FAILED
    }

}