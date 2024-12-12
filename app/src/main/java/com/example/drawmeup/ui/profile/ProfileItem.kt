package com.example.drawmeup.ui.profile

import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.databinding.ProfileItemBinding

class ProfileItem(private val profileItemBinding: ProfileItemBinding, private val onPostClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(profileItemBinding.root) {

    fun onBind(postItem: Post) = with(profileItemBinding) {
        postImage.setImageBitmap(postItem.postData)
        postImage.setOnClickListener {
            onPostClick(postItem.id)
        }
    }

}