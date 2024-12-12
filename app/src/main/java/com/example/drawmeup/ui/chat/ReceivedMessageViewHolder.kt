package com.example.drawmeup.ui.chat

import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Message
import com.example.drawmeup.databinding.MsgReceiverItemBinding

class ReceivedMessageViewHolder(private val msgReceiverItemBinding: MsgReceiverItemBinding) :
    RecyclerView.ViewHolder(msgReceiverItemBinding.root) {

    val userRepository = RepositoryLocator.userRepository

    suspend fun onBind(
        messageItem: Message,
    ) = with(msgReceiverItemBinding) {

        val user = userRepository.getById(messageItem.senderId).toUser()

        userAvatar.setImageBitmap(user.avatar)
        messageText.text = messageItem.message
        timeTextView.text = messageItem.date
    }

}