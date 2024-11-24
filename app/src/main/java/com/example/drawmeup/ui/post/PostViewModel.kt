package com.example.drawmeup.ui.post

import UserSession
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Comment
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    var postRepository = RepositoryLocator.postRepository
    var commentRepository = RepositoryLocator.commentRepository

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    val tags = MutableLiveData("")
    val image = MutableLiveData(Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888))
    val author = MutableLiveData("")
    val likes = MutableLiveData("")

    val commentList = MutableLiveData(emptyList<Comment>())

    fun init(id: Int) {
        Logger.debug("ViewModel post $id")
    }

    fun loadComments(id: Int) {
        viewModelScope.launch {
            commentList.value = commentRepository.getComments(id)
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch {
            commentRepository.deleteComment(comment)
            loadComments(comment.postId)
        }
    }
}
