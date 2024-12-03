package com.example.drawmeup.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drawmeup.databinding.FragmentProfileBinding
import com.example.drawmeup.utils.Logger

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileListAdapter: ProfileListAdapter
    private val viewModel: ProfileViewModel by viewModels()

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
        profileListAdapter = ProfileListAdapter(viewModel::postEdit)

        binding.profileRecyclerView.apply {
            addItemDecoration(GridSpacingItemDecoration(10))
            //TODO: Be aware might be buggy
            layoutManager = GridLayoutManager(context, 3)
            adapter = profileListAdapter
        }

        viewModel.postList.observe(viewLifecycleOwner) {
            profileListAdapter.postList = it
            Logger.debug(it.toString())
        }

        viewModel.navigation.observe(viewLifecycleOwner) {
            it.resolve(findNavController())
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadPosts()
    }

}