package com.example.drawmeup.ui.conversations

import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.R
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Conversation
import com.example.drawmeup.databinding.ConversationItemBinding

class ConversationItem(private val conversationItemBinding: ConversationItemBinding) :
RecyclerView.ViewHolder(conversationItemBinding.root) {
    private val conversationParticipantRepository = RepositoryLocator.conversationParticipantRepository
    private val messageRepository = RepositoryLocator.messageRepository
    private val userRepository = RepositoryLocator.userRepository


    suspend fun onBind(
        conversationItem: Conversation,
        onItemClick: (Int) -> Unit
    ) = with(conversationItemBinding) {
        val otherUserConversationParticipant = conversationParticipantRepository.getParticipants(conversationItem.id).first{
            it.userId != UserSession.user.id
        }
        val user = userRepository.getById(otherUserConversationParticipant.userId)

        //TODO: Replace with real avatar in future
        userAvatar.setImageResource(R.drawable.obraz_2023_08_20_235249287)
        userName.text = user.name

        val lastMessage = messageRepository.getMessages(conversationItem.id).first()

        var messageTextFormated: String
        if (lastMessage.senderId == UserSession.user.id) {
            messageTextFormated = "You: ${lastMessage.message}"
        } else {
            messageTextFormated = "${user.name}: ${lastMessage.message}"
        }
        messageText.text = messageTextFormated
        dateTextView.text = lastMessage.date

        root.setOnClickListener {
            onItemClick(conversationItem.id)
        }
    }
}