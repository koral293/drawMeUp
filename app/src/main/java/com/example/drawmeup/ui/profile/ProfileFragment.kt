package com.example.drawmeup.ui.profile

import android.app.Activity
import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.drawmeup.MainActivity
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentProfileBinding
import com.example.drawmeup.utils.Logger

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileListAdapter: ProfileListAdapter
    private val viewModel: ProfileViewModel by viewModels()
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
                            binding.userAvatar.setImageBitmap(resource)
                            viewModel.changeAvatar(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentProfileBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                it.viewModel = viewModel
                binding.lifecycleOwner = this
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            profileListAdapter = ProfileListAdapter(viewModel::postEdit)

            binding.userAvatar.setImageBitmap(viewModel.avatar.value)

            binding.profileRecyclerView.apply {
                addItemDecoration(GridSpacingItemDecoration(10))
                //TODO: Be aware might be buggy
                layoutManager = GridLayoutManager(context, 3)
                adapter = profileListAdapter
            }

            postList.observe(viewLifecycleOwner) {
                profileListAdapter.postList = it
                Logger.debug(it.toString())
            }

            navigation.observe(viewLifecycleOwner) {
                it.resolve(findNavController())
            }

            binding.logoutButton.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(R.string.logout)
                builder.setMessage(R.string.logout_confirm_warning)

                builder.setPositiveButton(R.string.yes_button) { dialog, _ ->
                    dialog.dismiss()
                    viewModel.logout()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

                builder.setNegativeButton(R.string.no_button) { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
            }

            binding.userAvatar.setOnClickListener {
                pickImage.launch(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadPosts()
    }

}