package com.example.drawmeup.ui.profile

import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.databinding.ProfileItemBinding

class ProfileItem(private val profileItemBinding: ProfileItemBinding) :
    RecyclerView.ViewHolder(profileItemBinding.root) {

    fun onBind(postItem: Post) = with(profileItemBinding) {
        postImage.setImageBitmap(postItem.postData)
    }

}