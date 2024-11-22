package com.example.drawmeup.ui.profile

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.databinding.ProfileItemBinding

class ProfileItem(myContext: Context, private val profileItemBinding: ProfileItemBinding) :
    RecyclerView.ViewHolder(profileItemBinding.root) {

        suspend fun onBind(
            postItem: Post
        ) = with(profileItemBinding) {
            postImage.setImageBitmap(postItem.postData)
        }

}