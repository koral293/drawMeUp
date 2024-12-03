package com.example.drawmeup.ui.profile

import UserSession
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.navigation.Destination
import com.example.drawmeup.navigation.PostNav
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val postRepository = RepositoryLocator.postRepository
    val postList: MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val userName: MutableLiveData<String> = MutableLiveData(UserSession.user.name)

    val navigation = MutableLiveData<Destination>()
    fun loadPosts() {
        viewModelScope.launch {
            postList.value = postRepository.getAll()
        }
    }

    fun postEdit(id: Int) {
        Logger.debug("View post $id")
        navigation.value = PostNav(id, false)
    }

}