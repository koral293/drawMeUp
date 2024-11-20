package com.example.drawmeup.data

import android.content.Context
import com.example.drawmeup.data.interfaces.LikesInterface
import com.example.drawmeup.data.interfaces.PostInterface
import com.example.drawmeup.data.interfaces.UserInterface
import com.example.drawmeup.data.repository.LikesRepository
import com.example.drawmeup.data.repository.PostRepository
import com.example.drawmeup.data.repository.UserRepository
import kotlinx.coroutines.runBlocking

object RepositoryLocator {
    lateinit var userRepository: UserInterface
    lateinit var postRepository: PostInterface
    lateinit var likesRepository: LikesInterface

    fun init(context: Context) {
        userRepository = UserRepository(context)
        postRepository = PostRepository(context)
        likesRepository = LikesRepository(context)

        runBlocking {
            userRepository.testData()
            postRepository.testData()
            likesRepository.testData()
        }
    }
}