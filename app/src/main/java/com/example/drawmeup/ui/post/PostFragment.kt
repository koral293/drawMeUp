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
import com.example.drawmeup.databinding.FragmentPostBinding
import com.example.drawmeup.navigation.ActionStatus
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private val viewModel: PostViewModel by viewModels()

    private val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri = data?.data
                Glide.with(this)
                    .asBitmap() // Wymuszamy załadowanie jako Bitmap
                    .load(imageUri)
                    .override(256, 256)
                    .into(object : CustomTarget<Bitmap>() { // CustomTarget, zamiast ImageView
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            // Ustawiamy Bitmap w ImageView
                            binding.postArtImageView.setImageBitmap(resource)
                            viewModel.image.value = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // Opcjonalnie - zwalnianie zasobów, jeśli potrzebne
                        }
                    })
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPostBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                it.viewModel = viewModel
                binding.lifecycleOwner = this
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.GONE
        binding.postArtImageView.setOnClickListener {
            pickImage.launch(intent)
        }
        binding.addButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val status = viewModel.addPost()
                if (ActionStatus.SUCCESS == status) {
                    findNavController().navigate(R.id.action_postFragment_to_navigation_dashboard)

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