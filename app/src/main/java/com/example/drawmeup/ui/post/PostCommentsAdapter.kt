package com.example.drawmeup.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.models.Comment
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.databinding.CommentItemBinding
import com.example.drawmeup.databinding.PostItemBinding
import kotlinx.coroutines.runBlocking

class PostCommentsAdapter(
    private val deleteItem: (Comment) -> Unit
) : RecyclerView.Adapter<CommentItem>() {
    var commentList: List<Comment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CommentItemBinding.inflate(layoutInflater, parent, false)

        return CommentItem(parent.context, binding)
    }

    override fun onBindViewHolder(holder: CommentItem, position: Int) {
        runBlocking {
            holder.onBind(commentList[position], deleteItem)
        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}