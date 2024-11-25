package com.example.drawmeup.ui.guest

import UserSession
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.interfaces.UserInterface
import com.example.drawmeup.data.models.User
import com.example.drawmeup.databinding.FragmentSignUpBinding
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class SignUpViewModel : ViewModel() {

    val nickname = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private val userRepository = RepositoryLocator.userRepository

    suspend fun onSubmit(binding: FragmentSignUpBinding): ActionStatus {
        var isError = false
        if (nickname.value!!.length < 3) {
            binding.nicknameEditText.error = "Nickname must be at least 3 characters long"
            isError = true
        }
        if (!email.value!!.contains("@")) {
            binding.singUpEmailEditText.error = "Invalid email address"
            isError = true
        }
        if (password.value!!.length < 8) {
            binding.singUpPasswordEditText.error = "Password must be at least 8 characters long"
            isError = true
        }

        if (isError) {
            return ActionStatus.FAILED
        }

        var result =
            withContext(Dispatchers.IO) { userRepository.getByEmail(email.value.toString()) }

        if (result != null) {
            Logger.debug("This email is already in use: ${email.value}")
            binding.singUpEmailEditText.error = "This email is already in use"
            isError = true
        }

        result = withContext(Dispatchers.IO) { userRepository.getByName(nickname.value.toString()) }

        if (result != null) {
            Logger.debug("This nickname is already in use: ${nickname.value}")
            binding.nicknameEditText.error = "This nickname is already in use"
            isError = true
        }

        if (isError) {
            return ActionStatus.FAILED
        }

        val newUser = User(
            0,
            nickname.value.toString(),
            email.value.toString().lowercase(),
            password.value.toString()
        )

        withContext(Dispatchers.IO) { userRepository.createOrUpdate(newUser) }

        result = withContext(Dispatchers.IO) { userRepository.getByName(nickname.value.toString()) }

        if (result != null) {
            Logger.debug("User created: $newUser")

            UserSession.user = result.toUser()
            UserSession.isLogged = true
            UserSession.lastLogged = Date()
            UserSession.saveSession()

            return ActionStatus.SUCCESS
        }

        return ActionStatus.FAILED
    }
}