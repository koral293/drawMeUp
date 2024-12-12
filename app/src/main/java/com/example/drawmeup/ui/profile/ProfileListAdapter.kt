package com.example.drawmeup.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.databinding.ProfileItemBinding
import kotlinx.coroutines.runBlocking

class ProfileListAdapter(
    private val onItemClick: (Int) -> Unit,
) :
    RecyclerView.Adapter<ProfileItem>(
    ) {

    var postList: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProfileItemBinding.inflate(layoutInflater, parent, false)

        return ProfileItem(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ProfileItem, position: Int) {
        runBlocking {
            holder.onBind(postList[position])
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}