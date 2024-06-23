package com.dicoding.picodiploma.loginwithanimation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import kotlinx.coroutines.launch

class StoryViewModel(storyRepository: StoryRepository): ViewModel() {

    val story: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory().cachedIn(viewModelScope)

}

