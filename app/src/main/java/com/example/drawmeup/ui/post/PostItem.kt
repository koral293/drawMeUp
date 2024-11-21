package com.example.drawmeup.ui.post

import UserSession
import android.annotation.SuppressLint
import android.content.Context
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.databinding.PostItemBinding
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.runBlocking

class PostItem(myContext: Context, private val postItemBinding: PostItemBinding) :
    RecyclerView.ViewHolder(postItemBinding.root) {
    private val userRepository = RepositoryLocator.userRepository
    private val likesRepository = RepositoryLocator.likesRepository
    private val context = myContext

    @SuppressLint("SetTextI18n")
    suspend fun onBind(
        postItem: Post,
        onItemClick: (Int) -> Unit,
        onLikeClick: (Int, Int, Boolean) -> Unit
    ) = with(postItemBinding) {
        Logger.debug("Image data: ${postItem.postData.height} x ${postItem.postData.width}")
        val bitmapWidth = postItem.postData.width
        val bitmapHeight = postItem.postData.height
        val aspectRatio = bitmapWidth.toFloat() / bitmapHeight.toFloat()

        artImage.scaleType = ImageView.ScaleType.CENTER_CROP

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val currentWindowMetrics = windowManager.currentWindowMetrics
        val displayMetrics = currentWindowMetrics.bounds
        val screenWidth = displayMetrics.width()
        val imageViewHeight = (screenWidth / aspectRatio).toInt()
        val layoutParams = artImage.layoutParams

        layoutParams.width = screenWidth
        layoutParams.height = imageViewHeight
        artImage.layoutParams = layoutParams
        artImage.setImageBitmap(postItem.postData)

        authorText.text = userRepository.getById(postItem.userId).name
        likesCountText.text = likesRepository.getCountForPost(postItem.id).toString()
        var isLiked = likesRepository.getLike(UserSession.user.id, postItem.id) != null

        if (isLiked) {
            likeButton.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            likeButton.setImageResource(android.R.drawable.btn_star_big_off)
        }

        //TODO: Track if user liked this post already
        likeButton.setOnClickListener {
            onLikeClick(UserSession.user.id, postItem.id, isLiked)

            if (isLiked) {
                likeButton.setImageResource(android.R.drawable.btn_star_big_off)
                likesCountText.text = ((likesCountText.text as String).toInt() - 1).toString()
                isLiked = false
            } else {
                likeButton.setImageResource(android.R.drawable.btn_star_big_on)
                likesCountText.text = ((likesCountText.text as String).toInt() + 1).toString()
                isLiked = true
            }
        }

        root.setOnClickListener {
            onItemClick(postItem.id)
        }

        artImage.setOnClickListener {
            Logger.debug("Image clicked")
            onItemClick(postItem.id)

        }
    }
}