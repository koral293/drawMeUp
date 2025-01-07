package com.example.drawmeup.ui.home

import UserSession
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
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
    val postList: MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val searchText: MutableLiveData<String> = MutableLiveData("")
    val navigation = MutableLiveData<Destination>()

    fun onViewPost(id: Int) {
        Logger.debug("View post $id")
        navigation.value = PostNav(id, true)
    }

    fun onPostLike(userId: Int, postId: Int, isLiked: Boolean) {
        Logger.debug("User $userId Like post $postId")
        viewModelScope.launch {
            if (isLiked) {
                likesRepository.removeLike(Likes(userId, postId))
            } else {
                likesRepository.addLike(Likes(userId, postId))
            }
        }
    }

    fun loadPosts() {
        viewModelScope.launch {
            postList.value = postRepository.getAllExcludingUserId(UserSession.user.id)
        }
    }

    fun searchPosts() {
        viewModelScope.launch {
            Logger.debug("Search text: ${searchText.value}")
            val builtQuery = buildTagSearchQuery(searchText.value!!.split(" "))
            Logger.debug("Query: ${builtQuery.sql}")
            val filteredPosts = postRepository.searchPosts(builtQuery)
            Logger.debug("Found posts: $filteredPosts")
            postList.value = filteredPosts
        }
    }

    private fun buildTagSearchQuery(tags: List<String>): SupportSQLiteQuery {
        val queryBuilder = StringBuilder("SELECT * FROM post WHERE userId != ${UserSession.user.id} AND")
        tags.forEachIndexed { index, tag ->
            queryBuilder.append(" description LIKE '%$tag%'")
            if (index < tags.size - 1) {
                queryBuilder.append(" AND ")
            }
        }
        return SimpleSQLiteQuery(queryBuilder.toString())
    }

}