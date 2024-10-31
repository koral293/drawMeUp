package com.example.drawmeup.data

import android.content.Context
import com.example.drawmeup.data.interfaces.PostInterface
import com.example.drawmeup.data.interfaces.UserInterface
import com.example.drawmeup.data.repository.PostRepository
import com.example.drawmeup.data.repository.UserRepository
import kotlinx.coroutines.runBlocking

object RepositoryLocator {
    lateinit var userRepository: UserInterface
    lateinit var postRepository: PostInterface

    fun init(context: Context) {
        userRepository = UserRepository(context)
        postRepository = PostRepository(context)

        runBlocking {
            userRepository.testData()
        }
    }
}