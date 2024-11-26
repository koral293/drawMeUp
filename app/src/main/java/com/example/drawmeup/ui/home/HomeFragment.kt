package com.example.drawmeup.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentHomeBinding
import com.example.drawmeup.utils.Logger

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var postListAdapter: PostListAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                it.viewModel = viewModel
                binding.lifecycleOwner = this
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postListAdapter = PostListAdapter(viewModel::onViewPost, viewModel::onPostLike)
        binding.postListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postListAdapter
        }
        viewModel.postList.observe(viewLifecycleOwner) {
            postListAdapter.postList = it
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadPosts()
            binding.swipeRefreshLayout.isRefreshing = false
            Logger.debug("List refreshed")
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        viewModel.navigation.observe(viewLifecycleOwner) {
            it.resolve(findNavController())
        }

        binding.addPostButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addPostFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadPosts()
    }


}