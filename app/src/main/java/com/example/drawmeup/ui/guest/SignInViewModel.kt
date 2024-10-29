package com.example.drawmeup.ui.guest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private var userRepository = RepositoryLocator.userRepository

    suspend fun onSubmit(): ActionStatus {
        val result = withContext(Dispatchers.IO) {
            userRepository.getByEmailAndPassword(
                email.value.toString().lowercase(),
                password.value.toString()
            )
        }
        if (result != null) {
            Logger.debug("User found: $result")
            return ActionStatus.SUCCESS
        }
        return ActionStatus.FAILED
    }
}
