package com.example.drawmeup.ui.guest

import UserSession
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentWelcomeBinding
import com.example.drawmeup.utils.Logger

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
        super.onViewCreated(view, savedInstanceState)

        UserSession.loadSession()
        Logger.debug("UserSession: ${UserSession.user} lastLogged: ${UserSession.lastLogged}")

        //TODO: Check if user is session is not expired
        if (UserSession.isLogged) {
            navigateToHome()
        }

        binding.welcomeSignInButton.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_signInFragment)
        }
        binding.welcomeSignUpButton.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_signUpFragment)
        }
    }

    private fun navigateToHome() {
        val navController = findNavController()

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.navigation_home, true)
            .build()

        navController.navigate(R.id.navigation_home, null, navOptions)


    }

}