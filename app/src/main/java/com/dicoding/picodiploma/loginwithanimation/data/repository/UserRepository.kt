package com.dicoding.picodiploma.loginwithanimation.data.repository

import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.api.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class UserRepository private constructor(
    private val userPreference: UserPreference,
) {

    fun register(name: String, email: String, password: String): Call<RegisterResponse> {
        return ApiConfig.getApiService().register(name, email, password)
    //return ApiConfig.getApiService().register(name, email, password)
    }

    fun login(email: String, password: String): Call<LoginResponse> {
        return ApiConfig.getApiService().login(email, password)
    //return ApiService.login(email, password)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}