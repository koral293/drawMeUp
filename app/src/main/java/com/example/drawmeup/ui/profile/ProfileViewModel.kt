package com.example.drawmeup.ui.profile

import UserSession
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Post
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val postRepository = RepositoryLocator.postRepository
    val postList: MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val userName: MutableLiveData<String> = MutableLiveData(UserSession.user.name)

    fun loadPosts() {
        viewModelScope.launch {
            postList.value = postRepository.getAll()
        }
    }


}