package com.example.drawmeup.ui.post

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentAddPostBinding
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.navigation.PostType
import com.example.drawmeup.utils.Logger
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

private const val TYPE_KEY = "type"

class AddPostFragment : Fragment() {

    private lateinit var binding: FragmentAddPostBinding
    private val viewModel: AddPostViewModel by viewModels()
    private lateinit var type: PostType
    private val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri = data?.data
                Glide.with(this)
                    .asBitmap()
                    .load(imageUri)
                    .override(256, 256)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            binding.postArtImageView.setImageBitmap(resource)
                            viewModel.image.value = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            }
        }

    fun loadImage() {
        Glide.with(this)
            .asBitmap()
            .load(viewModel.image.value)
            .override(256, 256)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    binding.postArtImageView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = PostType.View(0)
        arguments?.let {
            type = it.getSerializable(TYPE_KEY, PostType::class.java) ?: PostType.View(0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAddPostBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                it.viewModel = viewModel
                binding.lifecycleOwner = this
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.GONE

        with(viewModel) {
            Logger.debug("Type: $type")
            (type as? PostType.View)?.let {
                Logger.debug("Post id: ${it.id}")
                init(it.id, ::loadImage)
            }
        }

        binding.postArtImageView.setOnClickListener {
            pickImage.launch(intent)
        }

        binding.addButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val status = viewModel.addPost()
                if (ActionStatus.SUCCESS == status) {
                    (type as? PostType.View)?.let {
                        if (it.id != 0) {
                            findNavController().navigate(R.id.action_addPostFragment_to_navigation_profile)
                        } else {
                            findNavController().navigate(R.id.action_addPostFragment_to_navigation_home)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility =
            View.VISIBLE
    }

}