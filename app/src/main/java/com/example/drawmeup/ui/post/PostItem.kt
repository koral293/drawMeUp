package com.example.drawmeup.ui.post

import UserSession
import android.content.Context
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Post
import com.example.drawmeup.databinding.PostItemBinding
import com.example.drawmeup.utils.Logger

class PostItem(myContext: Context, private val postItemBinding: PostItemBinding) :
    RecyclerView.ViewHolder(postItemBinding.root) {
    private val userRepository = RepositoryLocator.userRepository
    private val likesRepository = RepositoryLocator.likesRepository
    private val context = myContext

    suspend fun onBind(
        postItem: Post,
        onItemClick: (Int) -> Unit,
        onLikeClick: (Int, Int) -> Unit
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

        //TODO: Track if user liked this post already
        likeButton.setOnClickListener {
            onLikeClick(UserSession.user.id, postItem.id)
            likeButton.setImageResource(android.R.drawable.btn_star_big_on)
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