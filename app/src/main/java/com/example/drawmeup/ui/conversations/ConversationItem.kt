package com.example.drawmeup.ui.conversations

import UserSession
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
        val otherUserConversationParticipant =
            conversationParticipantRepository.getParticipants(conversationItem.id).first {
                it.userId != UserSession.user.id
            }
        val user = userRepository.getById(otherUserConversationParticipant.userId).toUser()

        userAvatar.setImageBitmap(user.avatar)
        userName.text = user.name

        val lastMessage = messageRepository.getMessages(conversationItem.id).firstOrNull()

        var messageTextFormated = R.string.message_template.toString()
        dateTextView.text = ""
        if (lastMessage != null) {
            messageTextFormated = if (lastMessage.senderId == UserSession.user.id) {
                "You: ${lastMessage.message}"
            } else {
                "${user.name}: ${lastMessage.message}"
            }
            dateTextView.text = lastMessage.date
        }
        messageText.text = messageTextFormated

        root.setOnClickListener {
            onItemClick(conversationItem.id)
        }
    }

}