package com.dicoding.picodiploma.loginwithanimation.view.uploadStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.repository.UserRepository
import kotlinx.coroutines.launch

class UploadViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userSession = MutableLiveData<UserModel>()
    val userSession: LiveData<UserModel> = _userSession

    init {
        getSession()
    }

    fun getSession() {
        viewModelScope.launch {
            userRepository.getSession().collect { user ->
                _userSession.value = user
            }
        }
    }
}
