package com.example.drawmeup.ui.guest.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentSignUpBinding
import com.example.drawmeup.navigation.ActionStatus
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSignUpBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                it.viewModel = viewModel
                binding.lifecycleOwner = this
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val status = viewModel.onSubmit(binding)
                if (ActionStatus.SUCCESS == status) {
                    findNavController().navigate(R.id.action_signUpFragment_to_navigation_home)
                }
            }
        }
    }

}