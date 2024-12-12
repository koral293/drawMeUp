package com.example.drawmeup.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drawmeup.R
import com.example.drawmeup.data.BottomNavigationManager
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
        with(viewModel) {

            postListAdapter = PostListAdapter(viewModel::onViewPost, viewModel::onPostLike)
            binding.postListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = postListAdapter
            }

            postList.observe(viewLifecycleOwner) {
                postListAdapter.postList = it
            }

            binding.swipeRefreshLayout.setOnRefreshListener {
                viewModel.searchPosts()
                binding.swipeRefreshLayout.isRefreshing = false
                Logger.debug("List refreshed")
            }

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                requireActivity().finish()
            }

            navigation.observe(viewLifecycleOwner) {
                it.resolve(findNavController())
            }

            binding.addPostButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_addPostFragment)
            }

            binding.searchEditText.setOnEditorActionListener { _, i, _ ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    val currentFocus = requireActivity().currentFocus
                    if (currentFocus != null) {
                        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
                    }
                    viewModel.searchPosts()
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        BottomNavigationManager.show(requireActivity())
        viewModel.loadPosts()
    }


}