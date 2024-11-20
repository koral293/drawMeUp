package com.example.drawmeup.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Likes
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.navigation.Destination
import com.example.drawmeup.navigation.PostNav
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val postRepository = RepositoryLocator.postRepository
    private val likesRepository = RepositoryLocator.likesRepository

    val navigation = MutableLiveData<Destination>()

    fun onViewPost(id: Int) {
        Logger.debug("View post $id")
        navigation.value = PostNav(id)
    }

    fun onPostLike( userId: Int, postId: Int) {
        Logger.debug("User $userId Like post $postId")
        viewModelScope.launch {
            likesRepository.addLike(Likes(userId, postId))
        }
    }

    fun loadPosts() {
        viewModelScope.launch {
            postList.value = postRepository.getAll()
        }
    }

    val postList: MutableLiveData<List<Post>> = MutableLiveData(emptyList())

}