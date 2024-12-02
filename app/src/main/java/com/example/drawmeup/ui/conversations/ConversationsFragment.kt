package com.example.drawmeup.ui.conversations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentConversationsBinding
import com.example.drawmeup.utils.Logger
import com.google.android.material.bottomnavigation.BottomNavigationView

class ConversationsFragment : Fragment() {

    private lateinit var binding: FragmentConversationsBinding
    private lateinit var conversationsListAdapter: ConversationsListAdapter
    private val viewModel: ConversationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentConversationsBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                it.viewModel = viewModel
                binding.lifecycleOwner = this
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conversationsListAdapter = ConversationsListAdapter(viewModel::onViewConversation)
        binding.conversationsListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = conversationsListAdapter
        }
        viewModel.conversationList.observe(viewLifecycleOwner) {
            conversationsListAdapter.conversationList = it

        }

        viewModel.navigation.observe(viewLifecycleOwner) {
            it.resolve(findNavController())
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadConversations()
            binding.swipeRefreshLayout.isRefreshing = false
            Logger.debug("List refreshed")
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility =
            View.VISIBLE
        viewModel.loadConversations()
    }

}