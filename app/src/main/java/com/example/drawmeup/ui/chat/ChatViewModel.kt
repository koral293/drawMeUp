package com.example.drawmeup.ui.chat

import UserSession
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.R
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

    private var conversationId: Int = 0
    val messageText = MutableLiveData("")
    val chatList: MutableLiveData<List<Message>> = MutableLiveData(emptyList())
    val user: MutableLiveData<User> = MutableLiveData(User(0, "", "", "", Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)))
    private val conversationParticipantRepository = RepositoryLocator.conversationParticipantRepository
    private val messageRepository = RepositoryLocator.messageRepository
    private val userRepository = RepositoryLocator.userRepository

    fun initChat(id: Int) {
        conversationId = id
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
                val refreshedMessages = messageRepository.getMessages(conversationId).reversed()
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
            chatList.value = messageRepository.getMessages(conversationId).reversed()
        }
    }

    fun sendMessage(): ActionStatus {
        if (messageText.value?.isNotEmpty() == true) {
            runBlocking {
                messageRepository.sendMessage(
                    Message(
                        0,
                        conversationId,
                        UserSession.user.id,
                        messageText.value.toString(),
                        DateTimeFormatter.ofPattern(R.string.date_format.toString())
                            .format(LocalDateTime.now()).toString()
                    )
                )
                loadMessages()
            }
            messageText.value = ""
            return ActionStatus.SUCCESS
        }
        return ActionStatus.FAILED
    }

}