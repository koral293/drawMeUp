package com.example.drawmeup.ui.guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentWelcomeBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                navController = findNavController()
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //TODO: Check if user is cached if so skip to main dashboard
        super.onViewCreated(view, savedInstanceState)
        binding.welcomeSignInButton.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_signInFragment)
        }
        binding.welcomeSignUpButton.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_signUpFragment)
        }
    }

}