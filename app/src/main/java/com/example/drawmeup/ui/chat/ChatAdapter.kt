package com.example.drawmeup.ui.chat

import UserSession
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.R
import com.example.drawmeup.data.models.Conversation
import com.example.drawmeup.data.models.Message
import com.example.drawmeup.databinding.ConversationItemBinding
import com.example.drawmeup.databinding.MsgReceiverItemBinding
import com.example.drawmeup.databinding.MsgSenderItemBinding

class ChatAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(
) {
    var chatList: List<Message> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].senderId == UserSession.user.id) VIEW_TYPE_MESSAGE_SENT else VIEW_TYPE_MESSAGE_RECEIVED
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
            holder.onBind(message)
        }
    }

    override fun getItemCount(): Int = chatList.size

    class SentMessageViewHolder(private val msgSenderItemBinding: MsgSenderItemBinding) : RecyclerView.ViewHolder(msgSenderItemBinding.root) {


        fun onBind(
            messageItem: Message,
        ) = with(msgSenderItemBinding) {

            //TODO: Replace with real avatar in future
            userAvatar.setImageResource(com.example.drawmeup.R.drawable.obraz_2023_08_20_235249287)
            messageText.text = messageItem.message
            timeTextView.text = messageItem.date
        }
    }

        // ViewHolder dla odebranych wiadomości
        class ReceivedMessageViewHolder(private val msgReceiverItemBinding: MsgReceiverItemBinding) :
            RecyclerView.ViewHolder(msgReceiverItemBinding.root) {

                fun onBind(
                messageItem: Message,
            ) = with(msgReceiverItemBinding) {

                //TODO: Replace with real avatar in future
                userAvatar.setImageResource(com.example.drawmeup.R.drawable.obraz_2023_08_20_235249287)
                messageText.text = messageItem.message
                timeTextView.text = messageItem.date
            }
        }

    }