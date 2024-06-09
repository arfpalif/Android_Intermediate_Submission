package com.dicoding.picodiploma.loginwithanimation.di

import android.content.Context
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideStoryRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return StoryRepository.getInstance(pref)
    }

    fun provideRepository(context: Context): UserRepository {
        return UserRepository.getInstance(UserPreference.getInstance(context.dataStore))
    }
}
