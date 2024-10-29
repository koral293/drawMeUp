package com.example.drawmeup.ui.guest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawmeup.data.RepositoryLocator
import com.example.drawmeup.data.interfaces.UserInterface
import com.example.drawmeup.data.models.User
import com.example.drawmeup.databinding.FragmentSignUpBinding
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.Logger
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    val nickname = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    fun onSubmit(binding: FragmentSignUpBinding): ActionStatus {
        //TODO: Check if the user already exists
        if (!nickname.value.isNullOrEmpty() && !email.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
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

            val newUser = User(
                UserInterface.GENERATE_ID,
                nickname.value.toString(),
                email.value.toString(),
                password.value.toString()
            )
            viewModelScope.launch {
                //TODO Add check verification
                RepositoryLocator.userRepository.createOrUpdate(newUser)
            }
            Logger.debug("User created: $newUser")
            return ActionStatus.SUCCESS
        }
        return ActionStatus.FAILED
    }
}