package com.example.usw_random_chat.domain.usecase

import com.example.usw_random_chat.data.dto.Token
import com.example.usw_random_chat.data.dto.UserDTO
import com.example.usw_random_chat.domain.repository.SignInRepository

class SignInUseCase(private val signInRepository: SignInRepository) {
    suspend fun execute(param : UserDTO) : Int {
        return signInRepository.signIn(param)
    }

    suspend fun autoSignIn(token: String) : Int {
        return signInRepository.autoSignIn(token)
    }
}