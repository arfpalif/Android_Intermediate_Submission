package com.dicoding.picodiploma.loginwithanimation.data.response

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @SerializedName("userId") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("token") val token: String
)

class RegisterResponse (
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("loginResult") val loginResult: LoginResult?
)





