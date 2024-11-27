package com.example.drawmeup.ui.post

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentPostBinding
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.navigation.PostType
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TYPE_KEY = "type"

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private val viewModel: PostViewModel by viewModels()
    private lateinit var type: PostType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getSerializable(TYPE_KEY, PostType::class.java) ?: PostType.View(0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.GONE
        return FragmentPostBinding.inflate(layoutInflater, container, false).also {
            binding = it
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewModel) {
            //TODO: What is purpose of that?????
            (type as? PostType.View)?.let {
                init(it.id, ::loadImage)
                loadComments()
            }

            val commentListAdaper = PostCommentsAdapter(viewModel::deleteComment)

            binding.commentsList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = commentListAdaper
            }
            viewModel.commentList.observe(viewLifecycleOwner) {
                commentListAdaper.commentList = it
            }

            //TODO: Image like in post view

            binding.sendCommentButton.setOnClickListener {
                val status = addComment()
                if (status == ActionStatus.SUCCESS) {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "Komentarz dodany",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val currentFocus = requireActivity().currentFocus // Get the currently focused view
                if (currentFocus != null) {
                    imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
                }
            }

            binding.likePostButton.setOnClickListener {
                viewModel.onPostLike(UserSession.user.id, ::setLikeButtonImage)

            }
        }
    }

    private fun loadImage(image: Bitmap) {
        binding.imageView.setImageBitmap(image)
        setLikeButtonImage(viewModel.isLiked.value!!)
    }

    private fun setLikeButtonImage(isLiked: Boolean) {
        if (isLiked) {
            binding.likePostButton.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            binding.likePostButton.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility =
            View.VISIBLE
    }

}
