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

    var userRepository = RepositoryLocator.userRepository

//    fun onSubmit(): ActionStatus {
//        var actionStatus = ActionStatus.FAILED
//        if (!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
//            viewModelScope.launch {
//                withContext(Dispatchers.Main) {
//                    val result = userRepository.getByEmailAndPassword(
//                        email.value.toString(),
//                        password.value.toString()
//                    )
//
//                    if (result != null) {
//                        actionStatus = ActionStatus.SUCCESS
//                    }
//                }
//            }
//            return actionStatus
//        }
//        return ActionStatus.FAILED
//    }

    suspend fun onSubmit(): ActionStatus {
        return if (!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
            //TODO: This does not work :(
            val result = withContext(Dispatchers.IO) {
                userRepository.getByEmailAndPassword(
                    email.value.toString(),
                    password.value.toString()
                )
            }
            if (result != null) {
                Logger.debug("User found: $result")
                ActionStatus.SUCCESS
            } else {
                ActionStatus.FAILED
            }
        } else {
            ActionStatus.FAILED
        }
    }


}