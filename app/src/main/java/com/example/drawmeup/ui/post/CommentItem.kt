package com.example.drawmeup.ui.post

import UserSession
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.Comment
import com.example.drawmeup.databinding.CommentItemBinding

class CommentItem(myContext: Context, private val commentItemBinding: CommentItemBinding) :
    RecyclerView.ViewHolder(commentItemBinding.root) {
    private val userRepository = RepositoryLocator.userRepository
    private val likesRepository = RepositoryLocator.likesRepository
    private val context = myContext

    suspend fun onBind(
        comment: Comment,
        deleteItem: (Comment) -> Unit
    ) = with(commentItemBinding) {

        userAvatar.setImageBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
        userName.text = userRepository.getById(comment.userId).name
        commentText.text = comment.comment
        dateTextView.text = comment.date


        root.setOnLongClickListener {
            if (comment.userId == UserSession.user.id) {
                val builder: AlertDialog = AlertDialog.Builder(root.context)
                    .setTitle("Usuwanie komentarza")
                    .setMessage("Czy na pewno chcesz usunąć ten komentarz?")
                    .setPositiveButton("Tak") { _, _ ->
                        deleteItem(comment)
                    }
                    .setNegativeButton("Nie") { _, _ -> }
                    .create()

                builder.show()
            } else {
                Toast.makeText(
                    context,
                    "Nie można usuwać nie swoich komentarzy",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }
    }
}