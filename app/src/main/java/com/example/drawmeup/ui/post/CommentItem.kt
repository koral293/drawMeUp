package com.example.drawmeup.ui.post

import UserSession
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.R
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Comment
import com.example.drawmeup.databinding.CommentItemBinding

class CommentItem(myContext: Context, private val commentItemBinding: CommentItemBinding) :
    RecyclerView.ViewHolder(commentItemBinding.root) {

    private val userRepository = RepositoryLocator.userRepository
    private val context = myContext

    suspend fun onBind(
        comment: Comment,
        deleteItem: (Comment) -> Unit
    ) = with(commentItemBinding) {
        val user = userRepository.getById(comment.userId)
        userAvatar.setImageBitmap(user.toUser().avatar)
        userName.text = user.name
        commentText.text = comment.comment
        dateTextView.text = comment.date

        root.setOnLongClickListener {
            if (comment.userId == UserSession.user.id) {
                val builder: AlertDialog = AlertDialog.Builder(root.context)
                    .setTitle(R.string.remove_comment)
                    .setMessage(R.string.confirm_comment_removal_warning)
                    .setPositiveButton(R.string.yes_button) { _, _ ->
                        deleteItem(comment)
                    }
                    .setNegativeButton(R.string.no_button) { _, _ -> }
                    .create()

                builder.show()
            } else {
                Toast.makeText(
                    context,
                    R.string.delete_not_own_comment_warning,
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }
    }
}