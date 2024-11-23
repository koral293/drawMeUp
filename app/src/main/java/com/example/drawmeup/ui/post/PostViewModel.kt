package com.example.drawmeup.ui.post

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.utils.Logger

class PostViewModel : ViewModel() {
    var postRepository = RepositoryLocator.postRepository

    val name = MutableLiveData("")
    val description = MutableLiveData("")
    val tags = MutableLiveData("")
    val image = MutableLiveData(Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888))
    val author = MutableLiveData("")
    val likes = MutableLiveData("")

    fun init(id: Int) {
        Logger.debug("ViewModel post $id")
    }
}
