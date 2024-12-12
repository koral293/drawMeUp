package com.example.drawmeup.ui.chat

import android.content.Context
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
import com.example.drawmeup.data.BottomNavigationManager
import com.example.drawmeup.databinding.FragmentChatBinding
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.navigation.ChatType

class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatListAdapter: ChatAdapter
    private lateinit var type: ChatType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getSerializable("type", ChatType::class.java)
                ?: ChatType.View(0)
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
            (type as? ChatType.View)?.let {
                initChat(it.id)
            }
            BottomNavigationManager.hide(requireActivity())

            chatListAdapter = ChatAdapter()
            binding.conversationsListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = chatListAdapter
            }

            chatList.observe(viewLifecycleOwner) {
                chatListAdapter.chatList = it
            }

            binding.sendMessageButton.setOnClickListener {
                val status = sendMessage()
                if (status == ActionStatus.SUCCESS) {
                    Toast.makeText(
                        requireContext().applicationContext,
                        R.string.message_sent,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val currentFocus = requireActivity().currentFocus
                if (currentFocus != null) {
                    imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        BottomNavigationManager.show(requireActivity())
    }

}