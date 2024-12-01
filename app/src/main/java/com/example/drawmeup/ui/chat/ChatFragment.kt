package com.example.drawmeup.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentChatBinding
import com.example.drawmeup.databinding.FragmentConversationsBinding
import com.example.drawmeup.navigation.ChatNav
import com.example.drawmeup.navigation.ChatType
import com.example.drawmeup.navigation.PostType
import com.example.drawmeup.ui.conversations.ConversationsListAdapter
import com.example.drawmeup.ui.conversations.ConversationsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TYPE_KEY = "type"

class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding
    lateinit var chatListAdapter: ChatAdapter
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var type: ChatType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getSerializable(com.example.drawmeup.ui.chat.TYPE_KEY, ChatType::class.java) ?: ChatType.View(0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentChatBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                it.viewModel = viewModel
                binding.lifecycleOwner = this
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            //TODO: What is purpose of that?????
            (type as? ChatType.View)?.let {
                initChat(it.id)
            }
            }
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.GONE

        chatListAdapter = ChatAdapter()
        binding.conversationsListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatListAdapter
        }
        viewModel.chatList.observe(viewLifecycleOwner) {
            chatListAdapter.chatList = it

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility =
            View.VISIBLE
    }
}