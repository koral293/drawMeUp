package com.example.drawmeup.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.Logger

class PostViewModel : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    val tags = MutableLiveData("")

    fun addPost() : ActionStatus {
        val newPost = Post(0, 0, name.value!!.toString(), ByteArray(0), description.value!!.toString(), tags.value!!.split(" ").toMutableList() as ArrayList<String>)
        Logger.debug("New post: $newPost")
        return ActionStatus.SUCCESS
    }

}