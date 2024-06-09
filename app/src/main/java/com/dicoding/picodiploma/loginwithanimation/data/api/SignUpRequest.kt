package com.dicoding.picodiploma.loginwithanimation.data.api

import com.dicoding.picodiploma.loginwithanimation.data.response.LoginResult
import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

data class SignUpResponse(
    val error: Boolean,
    val message: String
)

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult
)

data class LoginResult(
    val name: String,
    val token: String
)



