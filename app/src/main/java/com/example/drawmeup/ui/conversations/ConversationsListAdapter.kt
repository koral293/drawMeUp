package com.example.drawmeup.ui.conversations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.models.Conversation
import com.example.drawmeup.databinding.ConversationItemBinding
import kotlinx.coroutines.runBlocking

class ConversationsListAdapter(
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ConversationItem>() {
    var conversationList: List<Conversation> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ConversationItemBinding.inflate(layoutInflater, parent, false)

        return ConversationItem(binding)
    }

    override fun getItemCount(): Int {
        return conversationList.size
    }

    override fun onBindViewHolder(holder: ConversationItem, position: Int) {
        runBlocking {
            holder.onBind(conversationList[position], onItemClick)
        }
    }

}