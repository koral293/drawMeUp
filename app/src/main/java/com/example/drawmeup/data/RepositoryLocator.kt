package com.example.drawmeup.data

import android.content.Context
import com.example.drawmeup.data.interfaces.CommentInterface
import com.example.drawmeup.data.interfaces.ConversationInterface
import com.example.drawmeup.data.interfaces.ConversationParticipantInterface
import com.example.drawmeup.data.interfaces.LikesInterface
import com.example.drawmeup.data.interfaces.MessageInterface
import com.example.drawmeup.data.interfaces.PostInterface
import com.example.drawmeup.data.interfaces.TokenInterface
import com.example.drawmeup.data.interfaces.UserInterface
import com.example.drawmeup.data.repository.CommentRepository
import com.example.drawmeup.data.repository.ConversationParticipantRepository
import com.example.drawmeup.data.repository.ConversationRepository
import com.example.drawmeup.data.repository.LikesRepository
import com.example.drawmeup.data.repository.MessageRepository
import com.example.drawmeup.data.repository.PostRepository
import com.example.drawmeup.data.repository.TokenRepository
import com.example.drawmeup.data.repository.UserRepository
import kotlinx.coroutines.runBlocking

object RepositoryLocator {
    lateinit var userRepository: UserInterface
    lateinit var postRepository: PostInterface
    lateinit var likesRepository: LikesInterface
    lateinit var commentRepository: CommentInterface
    lateinit var conversationRepository: ConversationInterface
    lateinit var conversationParticipantRepository: ConversationParticipantInterface
    lateinit var messageRepository: MessageInterface
    lateinit var tokenRepository: TokenInterface

    fun init(context: Context) {
        userRepository = UserRepository(context)
        postRepository = PostRepository(context)
        likesRepository = LikesRepository(context)
        commentRepository = CommentRepository(context)
        conversationRepository = ConversationRepository(context)
        conversationParticipantRepository = ConversationParticipantRepository(context)
        messageRepository = MessageRepository(context)
        tokenRepository = TokenRepository(context)

        runBlocking {
            if (userRepository.getAll().isEmpty()) {
                userRepository.testData()
                postRepository.testData()
                likesRepository.testData()
                commentRepository.testData()
                conversationRepository.testData()
                conversationParticipantRepository.testData()
                messageRepository.testData()
            }
        }
    }

}