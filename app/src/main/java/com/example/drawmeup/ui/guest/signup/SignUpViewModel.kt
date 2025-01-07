package com.example.drawmeup.ui.guest.signup

import UserSession
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawmeup.R
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.models.User
import com.example.drawmeup.databinding.FragmentSignUpBinding
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.Locale

class SignUpViewModel : ViewModel() {

    private val userRepository = RepositoryLocator.userRepository
    val nickname = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val avatar = MutableLiveData(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))

    suspend fun onSubmit(binding: FragmentSignUpBinding, callback: (ActionStatus) -> Unit ) {
        var isError = false
        if (nickname.value!!.length < 3) {
            binding.nicknameEditText.error = "Nickname must be at least 3 characters long"
            isError = true
        }
        if (!email.value!!.contains("@")) {
            binding.emailEditText.error = "Invalid email address"
            isError = true
        }
        if (password.value!!.length < 8) {
            binding.passwordEditText.error = "Password must be at least 8 characters long"
            isError = true
        }

        if (isError) {
            callback(ActionStatus.FAILED)
            return
        }

        var result = withContext(Dispatchers.IO) {
            userRepository.getByEmail(email.value.toString().lowercase(Locale.ROOT))
        }

        if (result != null) {
            Logger.debug("This email is already in use: ${email.value}")
            binding.emailEditText.error = "This email is already in use"
            isError = true
        }

        result = withContext(Dispatchers.IO) {
            userRepository.getByName(nickname.value.toString())
        }

        if (result != null) {
            Logger.debug("This nickname is already in use: ${nickname.value}")
            binding.nicknameEditText.error = "This nickname is already in use"
            isError = true
        }

        if (isError) {
            callback(ActionStatus.FAILED)
            return
        }

        val newUser = User(
            0,
            nickname.value.toString(),
            email.value.toString().lowercase(),
            password.value.toString(),
            avatar.value!!
        )

        withContext(Dispatchers.IO) {
            userRepository.createOrUpdate(newUser)


            result = userRepository.getByName(nickname.value.toString())

            if (result != null) {
                Logger.debug("User created: $result")

                UserSession.user = result!!.toUser()
                UserSession.isLogged = true
                UserSession.lastLogged = Date()
                UserSession.saveSession()

                callback(ActionStatus.SUCCESS)
            }

            callback(ActionStatus.FAILED)
        }
    }
}