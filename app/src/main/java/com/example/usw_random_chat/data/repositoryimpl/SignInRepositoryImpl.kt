package com.example.usw_random_chat.data.repositoryimpl

import android.util.Log
import com.example.usw_random_chat.data.api.SignInApiService
import com.example.usw_random_chat.data.dto.Token
import com.example.usw_random_chat.data.dto.UserDTO
import com.example.usw_random_chat.data.local.TokenSharedPreference
import com.example.usw_random_chat.domain.repository.SignInRepository
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val signInApiService: SignInApiService,
    private val tokenSharedPreference: TokenSharedPreference,
) : SignInRepository {

    override suspend fun signIn(param: UserDTO) : Int {
        val response = signInApiService.registerSignIn(param)

        return if (response.isSuccessful) {
            val accessToken = response.body()?.data?.accessToken
            val refreshToken = response.body()?.data?.refreshToken
            tokenSharedPreference.setToken("accessToken","$accessToken")
            tokenSharedPreference.setToken("refreshToken","$refreshToken")
            Log.d("Token","access: $accessToken, refresh: $refreshToken")
            response.code()
        } else {
            Log.d("로그인 실패","${response.body()}\n  ${response.code()}")
            response.code()
        }
    }

    override suspend fun autoSignIn(token: String): Int {
        val response = signInApiService.autoSignIn(token)

        return if (response.isSuccessful){
            val accessToken = response.body()?.data?.accessToken
            val refreshToken = response.body()?.data?.refreshToken
            tokenSharedPreference.setToken("accessToken","$accessToken")
            tokenSharedPreference.setToken("refreshToken","$refreshToken")
            Log.d("AutoLogin",response.body().toString())
            response.code()
        }
        else{
            Log.d("AutoLogin Fail",response.body().toString())
            response.code()
        }
    }
}