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
import java.util.Calendar
import java.util.Date

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

        if (UserSession.isLogged) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, -30)
            val date30DaysAgo = calendar.time
            if (UserSession.lastLogged.before(date30DaysAgo)) {
                Logger.debug("Session expired")
                UserSession.clearSession()
            } else {
                Logger.debug("Session valid")
                UserSession.lastLogged = Date()
                UserSession.saveSession()
                navigateToHome()
            }
        }

        binding.welcomeSignInButton.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_signInFragment)
        }
        binding.welcomeSignUpButton.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_signUpFragment)
        }
    }

    private fun navigateToHome() {
        Logger.debug("UserSession: ${UserSession.user} lastLogged: ${UserSession.lastLogged}")

        val navController = findNavController()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.navigation_home, true)
            .build()
        navController.navigate(R.id.navigation_home, null, navOptions)
    }

}