package com.dicoding.picodiploma.loginwithanimation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import kotlinx.coroutines.launch

class UserViewModel(private val userPreference : UserPreference) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    fun getToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }

    fun saveToken(token: String){
        viewModelScope.launch {
            userPreference.saveToken(token)
        }
    }


}
