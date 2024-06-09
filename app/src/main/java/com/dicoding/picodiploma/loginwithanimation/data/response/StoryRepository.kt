package com.dicoding.picodiploma.loginwithanimation.data

import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.StoryResponse
import kotlinx.coroutines.flow.first

class StoryRepository private constructor(private val userPreference: UserPreference) {

    suspend fun getStories(): StoryResponse {
        val token = userPreference.getToken().first()
        return ApiConfig.getApiService(token ?: "").getStories("Bearer $token")
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryRepository? = null

        fun getInstance(pref: UserPreference): StoryRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoryRepository(pref).also { INSTANCE = it }
            }
        }
    }
}
