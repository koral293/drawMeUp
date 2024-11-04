package com.example.drawmeup.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Post
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val postRepository = RepositoryLocator.postRepository

    fun loadPosts() {
        viewModelScope.launch {
            postList.value = postRepository.getAll()
        }
    }

    val postList: MutableLiveData<List<Post>> = MutableLiveData(emptyList())

}