package com.example.drawmeup.ui.chat

import UserSession
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.models.Message
import com.example.drawmeup.databinding.MsgReceiverItemBinding
import com.example.drawmeup.databinding.MsgSenderItemBinding
import kotlinx.coroutines.runBlocking

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(
) {

    companion object {
        const val VIEW_TYPE_MESSAGE_SENT = 0
        const val VIEW_TYPE_MESSAGE_RECEIVED = 1
    }

    var chatList: List<Message> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return when (chatList[position].senderId) {
            UserSession.user.id -> VIEW_TYPE_MESSAGE_SENT
            else -> VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            val binding = MsgSenderItemBinding.inflate(inflater, parent, false)
            SentMessageViewHolder(binding)
        } else {
            val binding = MsgReceiverItemBinding.inflate(inflater, parent, false)
            ReceivedMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = chatList[position]
        if (holder is SentMessageViewHolder) {
            holder.onBind(message)
        } else if (holder is ReceivedMessageViewHolder) {
            runBlocking {
                holder.onBind(message)
            }
        }
    }

    override fun getItemCount(): Int = chatList.size

}