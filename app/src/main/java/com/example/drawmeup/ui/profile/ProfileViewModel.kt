package com.example.drawmeup.ui.profile

import UserSession
import android.graphics.Bitmap
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


    private val userRepository = RepositoryLocator.userRepository
    private val postRepository = RepositoryLocator.postRepository
    val postList: MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val userName: MutableLiveData<String> = MutableLiveData(UserSession.user.name)
    val avatar: MutableLiveData<Bitmap> = MutableLiveData(UserSession.user.avatar)

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

    fun changeAvatar(bitmap: Bitmap) {
        avatar.value = bitmap
        UserSession.user.avatar = bitmap
        UserSession.saveSession()

        viewModelScope.launch {
            userRepository.createOrUpdate(UserSession.user)
        }
    }

    fun logout() {
        UserSession.clearSession()
    }

}