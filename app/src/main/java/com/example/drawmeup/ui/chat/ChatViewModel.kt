package com.example.drawmeup.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Message
import com.example.drawmeup.data.models.User
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {

    val chatList: MutableLiveData<List<Message>> = MutableLiveData(emptyList())
    val user: MutableLiveData<User> = MutableLiveData(User(0, "", "", ""))

    private val conversationParticipantRepository = RepositoryLocator.conversationParticipantRepository
    private val messageRepository = RepositoryLocator.messageRepository
    private val userRepository = RepositoryLocator.userRepository

    val comment = MutableLiveData("")

    fun initChat(chatId: Int) {

        viewModelScope.launch {
            val otherUserConversationParticipant = conversationParticipantRepository.getParticipants(chatId).first{
                it.userId != UserSession.user.id
            }

            user.value = userRepository.getById(otherUserConversationParticipant.userId).toUser()
            chatList.value = messageRepository.getMessages(chatId)
        }
    }

}