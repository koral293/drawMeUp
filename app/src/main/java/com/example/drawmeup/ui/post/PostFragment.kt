package com.example.drawmeup.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentPostBinding
import com.example.drawmeup.navigation.PostType
import com.example.drawmeup.utils.Logger
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.runBlocking

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
        // TODO: Use the ViewModel
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
            (type as? PostType.View)?.let { init(it.id) }

            runBlocking {
                val post = postRepository.getPostById((type as? PostType.View)?.id ?: 0)
                Logger.debug("Post found: $post")
                viewModel.name.value = post?.name
                viewModel.description.value = post?.description
                viewModel.image.value = post?.postData
                viewModel.tags.value = post?.tag?.joinToString { it }
                viewModel.author.value = post?.userId.toString()
                binding.imageView.setImageBitmap(post?.postData)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility =
            View.VISIBLE
    }
}