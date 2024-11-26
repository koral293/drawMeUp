package com.example.drawmeup.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.databinding.PostItemBinding
import kotlinx.coroutines.runBlocking

class PostListAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onLikeClick: (Int, Int, Boolean) -> Unit
) : RecyclerView.Adapter<PostItem>() {
    var postList: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(layoutInflater, parent, false)

        return PostItem(parent.context, binding)
    }

    override fun onBindViewHolder(holder: PostItem, position: Int) {
        runBlocking {
            holder.onBind(postList[position], onItemClick, onLikeClick)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
