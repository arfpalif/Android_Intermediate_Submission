package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.api.SignUpRequest
import com.dicoding.picodiploma.loginwithanimation.data.api.SignUpResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.view.component.EmailEditText
import com.dicoding.picodiploma.loginwithanimation.view.component.NameEditText
import com.dicoding.picodiploma.loginwithanimation.view.component.PasswordEditText
import com.dicoding.picodiploma.loginwithanimation.view.component.SignUpButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var emailEditText: EmailEditText
    private lateinit var nameEditText: NameEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var signUpButton: SignUpButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nameEditText = binding.edLoginName
        passwordEditText = binding.edLoginPassword
        emailEditText = binding.edLoginEmail
        signUpButton = binding.signupButton1
        setMyButtonEnable()

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            signUp(name, email, password)
        }

        setupView()
    }

    private fun setMyButtonEnable() {
        val result = nameEditText.text
        signUpButton.isEnabled = result != null && result.toString().isNotEmpty()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun signUp(name: String, email: String, password: String) {
        val request = SignUpRequest(name, email, password)
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.d("Tes123", response.toString())
                if (response.isSuccessful) {
                    Toast.makeText(this@SignupActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignupActivity, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "Sign Up Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
