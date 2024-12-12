package com.example.drawmeup.ui.post

import UserSession
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.R
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Comment
import com.example.drawmeup.data.models.Likes
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostViewModel : ViewModel() {
    private var postRepository = RepositoryLocator.postRepository
    private var commentRepository = RepositoryLocator.commentRepository
    private var userRepository = RepositoryLocator.userRepository
    private var likesRepository = RepositoryLocator.likesRepository
    private val tags = MutableLiveData("")
    val name = MutableLiveData("")
    val description = MutableLiveData("")
    val author = MutableLiveData("")
    val likes = MutableLiveData("")
    val comment = MutableLiveData("")
    var postId: Int = 0
    var isLiked = MutableLiveData(false)
    val commentList = MutableLiveData(emptyList<Comment>())

    fun init(id: Int, loadImage: (Bitmap) -> Unit) {
        postId = id
        viewModelScope.launch {
            val post = postRepository.getPostById(id)!!
            Logger.debug("Post found: $post")
            name.value = post.name
            description.value = post.description
            tags.value = post.tag.joinToString { it }
            author.value = userRepository.getById(post.userId).name
            likes.value = likesRepository.getCountForPost(id).toString()

            isLiked.value = likesRepository.getLike(UserSession.user.id, postId) != null
            Logger.debug("Is liked: ${isLiked.value}")

            loadImage(post.postData)
            loadComments()
        }
    }

    private fun loadComments() {
        viewModelScope.launch {
            commentList.value = commentRepository.getComments(postId)
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch {
            commentRepository.deleteComment(comment)
            loadComments()
        }
    }

    fun addComment(): ActionStatus {
        if (comment.value.toString().isNotEmpty()) {
            runBlocking {
                commentRepository.addComment(
                    Comment(
                        0,
                        UserSession.user.id,
                        postId,
                        comment.value.toString(),
                        DateTimeFormatter.ofPattern(R.string.date_format.toString())
                            .format(LocalDateTime.now()).toString()
                    )
                )
                loadComments()
            }
            comment.value = ""
            return ActionStatus.SUCCESS
        }
        return ActionStatus.FAILED
    }

    fun onPostLike(userId: Int, setLikeImage: (Boolean) -> Unit) {
        Logger.debug("User $userId Like post $postId")
        viewModelScope.launch {
            if (isLiked.value!!) {
                likesRepository.removeLike(Likes(userId, postId))
                likes.value = (likes.value!!.toInt() - 1).toString()
                isLiked.value = false
            } else {
                likesRepository.addLike(Likes(userId, postId))
                likes.value = (likes.value!!.toInt() + 1).toString()
                isLiked.value = true
            }
            setLikeImage(isLiked.value!!)
        }
    }

}
