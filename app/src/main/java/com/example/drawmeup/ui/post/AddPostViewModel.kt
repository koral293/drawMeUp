package com.example.drawmeup.ui.post

import UserSession
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.launch

class AddPostViewModel : ViewModel() {

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    val tags = MutableLiveData("")
    val buttonText = MutableLiveData("Dodaj")
    val postMutable = MutableLiveData<Post>()
    val image = MutableLiveData(Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888))

    private var postRepository = RepositoryLocator.postRepository

    suspend fun addPost(): ActionStatus {
        val id = UserSession.user.id
        val newPost = Post(
            0,
            id,
            name.value!!.toString(),
            image.value!!,
            description.value!!.toString(),
            tags.value!!.split(" ").toMutableList() as ArrayList<String>
        )

        if (postMutable.value != null) {
            newPost.id = postMutable.value!!.id
        }
        Logger.debug("New post: $newPost")
        val postId = postRepository.createOrUpdate(newPost)
        Logger.debug("New post id: $postId")

        val post = postRepository.getPostById(postId.toInt())

        if (post != null) {
            Logger.debug("Post found: $post")
            return ActionStatus.SUCCESS
        }
        return ActionStatus.FAILED
    }

    fun init(id: Int, loadImage: () -> Unit) {
        Logger.debug("Post id: $id")
        if (id != 0) {
            buttonText.value = "Edytuj"
            viewModelScope.launch {
                val post = postRepository.getPostById(id)!!
                Logger.debug("Post found: $post")
                name.value = post.name
                description.value = post.description
                tags.value = post.tag.joinToString { it }
                image.value = post.postData
                postMutable.value = post
                loadImage()
            }
        }
    }

}