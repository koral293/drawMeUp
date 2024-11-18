package com.example.drawmeup.ui.post

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.data.models.User
import kotlinx.coroutines.launch
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var postRepository = RepositoryLocator.postRepository
    var userRepository = RepositoryLocator.userRepository
    private var post: Post? = null
    private var user: User? = null

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    val tags = MutableLiveData("")
    val image = MutableLiveData<Bitmap>(Bitmap.createBitmap(256,256, Bitmap.Config.ARGB_8888))
    val author = MutableLiveData("")
    val likes = MutableLiveData("")



    fun init(id : Int){
        Logger.debug("ViewModel post $id")
        }
}
