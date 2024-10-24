package com.example.drawmeup.data

import android.content.Context
import com.example.drawmeup.data.interfaces.UserInterface
import com.example.drawmeup.data.repository.UserRepository
import kotlinx.coroutines.runBlocking

object RepositoryLocator {
    lateinit var userRepository: UserInterface

    fun init(context: Context) {
        userRepository = UserRepository(context)


        runBlocking {
            userRepository.testData()
        }
    }
}