package com.example.drawmeup.ui.conversations

import UserSession
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Conversation
import com.example.drawmeup.data.models.ConversationParticipant
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.navigation.ChatNav
import com.example.drawmeup.navigation.Destination
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ConversationsViewModel : ViewModel() {

    private val conversationParticipantRepository = RepositoryLocator.conversationParticipantRepository
    private val conversationRepository = RepositoryLocator.conversationRepository
    private val userRepository = RepositoryLocator.userRepository
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

    fun createNewConversation(username: String): ActionStatus {
        var status = ActionStatus.FAILED
        runBlocking {
            val user = userRepository.getByName(username)
            if (user != null) {
                if (conversationParticipantRepository.conversationExists(UserSession.user.id, user.id) == 0) {

                    val newConversationId = conversationRepository.createConversation(Conversation(0))

                    conversationParticipantRepository.addParticipant(
                        ConversationParticipant(newConversationId.toInt(), user.id)
                    )
                    conversationParticipantRepository.addParticipant(
                        ConversationParticipant(newConversationId.toInt(), UserSession.user.id)
                    )
                    status = ActionStatus.SUCCESS
                } else {
                    status = ActionStatus.EXISTS
                }
            }
        }
        return status
    }

}