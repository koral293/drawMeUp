package com.example.drawmeup.ui.conversations

import UserSession
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Conversation
import com.example.drawmeup.navigation.ChatNav
import com.example.drawmeup.navigation.Destination
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.launch

class ConversationsViewModel : ViewModel() {
    private val conversationParticipantRepository = RepositoryLocator.conversationParticipantRepository
    val conversationList: MutableLiveData<List<Conversation>> = MutableLiveData(emptyList())
    val navigation = MutableLiveData<Destination>()

    fun onViewConversation(id: Int) {
        Logger.debug("View conversation $id")
        navigation.value = ChatNav(id)
    }

    fun loadConversations() {
        viewModelScope.launch {
            val data = conversationParticipantRepository.getConversations(UserSession.user.id).map {
                Conversation(it.conversationId)
            }
            Logger.debug("Conversations loaded $data")
            conversationList.value = data
        }
    }
}