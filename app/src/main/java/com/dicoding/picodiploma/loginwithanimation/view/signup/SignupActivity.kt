package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.content.Intent
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
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.view.Home.HomeActivity
import com.dicoding.picodiploma.loginwithanimation.view.component.EmailEditText
import com.dicoding.picodiploma.loginwithanimation.view.component.NameEditText
import com.dicoding.picodiploma.loginwithanimation.view.component.PasswordEditText
import com.dicoding.picodiploma.loginwithanimation.view.component.SignUpButton
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity
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

        nameEditText.addTextChangedListener(textWatcher)
        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isValidInput(name, email, password)) {
                signUp(name, email, password)
            } else {
                Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }

        setupView()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            setMyButtonEnable()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun setMyButtonEnable() {
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        signUpButton.isEnabled = isValidInput(name, email, password)
    }

    private fun isValidInput(name: String, email: String, password: String): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
                emailEditText.error == null && passwordEditText.error == null
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
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        val request = SignUpRequest(name, email, password)
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.d("SignupActivity", response.toString())
                if (response.isSuccessful) {
                    startActivity(Intent(this@SignupActivity, HomeActivity::class.java))
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
