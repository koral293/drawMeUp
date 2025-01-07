package com.example.drawmeup.ui.guest.signin

import UserSession
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class SignInViewModel : ViewModel() {

    private var userRepository = RepositoryLocator.userRepository
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    suspend fun onSubmit(): ActionStatus {
        val result = withContext(Dispatchers.IO) {
            userRepository.getByEmailAndPassword(email.value.toString().lowercase(), password.value.toString())
        }

        if (result != null) {
            Logger.debug("User found: $result")
            UserSession.user = result.toUser()
            UserSession.isLogged = true
            UserSession.lastLogged = Date()
            UserSession.saveSession()

            return ActionStatus.SUCCESS
        }
        return ActionStatus.FAILED
    }
}

