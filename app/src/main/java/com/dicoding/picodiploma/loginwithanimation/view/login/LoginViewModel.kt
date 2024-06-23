package com.dicoding.picodiploma.loginwithanimation.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.api.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(val userPreference: UserPreference) : ViewModel() {
    val loginResponse = MutableLiveData<LoginResponse>()
    val errorMessage = MutableLiveData<String>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().login(email, password)
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userPreference.saveSession(user)
        }
    }
}