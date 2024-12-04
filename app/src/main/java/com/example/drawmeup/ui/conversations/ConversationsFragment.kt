package com.example.drawmeup.ui.conversations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentConversationsBinding
import com.example.drawmeup.navigation.ActionStatus
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

        binding.newConversationButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            val dialogLayout = layoutInflater.inflate(R.layout.dialog_data_layout, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.edit_text)

            builder.setView(dialogLayout).setPositiveButton("OK") { _, _ ->
                val status = viewModel.createNewConversation(editText.text.toString())
                when (status) {
                    ActionStatus.FAILED ->
                        Toast.makeText(
                            requireContext(),
                            "User does not exists!",
                            Toast.LENGTH_SHORT
                        ).show()

                    ActionStatus.EXISTS ->
                        Toast.makeText(
                            requireContext(),
                            "Conversation already exists!",
                            Toast.LENGTH_SHORT
                        ).show()

                    ActionStatus.SUCCESS -> {
                        Toast.makeText(
                            requireContext(),
                            "Conversation created!",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.loadConversations()
                    }

                }
            }.setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

    }

    override fun onStart() {
        super.onStart()
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)?.visibility =
            View.VISIBLE
        viewModel.loadConversations()
    }

}