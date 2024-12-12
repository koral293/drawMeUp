package com.example.drawmeup.ui.chat

import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.models.Message
import com.example.drawmeup.databinding.MsgSenderItemBinding

class SentMessageViewHolder(private val msgSenderItemBinding: MsgSenderItemBinding) :
    RecyclerView.ViewHolder(msgSenderItemBinding.root) {

    fun onBind(
        messageItem: Message,
    ) = with(msgSenderItemBinding) {
        userAvatar.setImageBitmap(UserSession.user.avatar)
        messageText.text = messageItem.message
        timeTextView.text = messageItem.date
    }

}