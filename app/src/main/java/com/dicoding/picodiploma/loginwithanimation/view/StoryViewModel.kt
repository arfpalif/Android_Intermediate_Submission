package com.dicoding.picodiploma.loginwithanimation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    private val _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> = _stories

    init {
        fetchStories()
    }

    private fun fetchStories() {
        viewModelScope.launch {
            try {
                val response = storyRepository.getStories()
                _stories.value = response.listStory
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

