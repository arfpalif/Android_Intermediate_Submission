package com.dicoding.picodiploma.loginwithanimation.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.api.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.api.LoginResult
import com.dicoding.picodiploma.loginwithanimation.data.api.SignUpRequest
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.home.HomeActivity
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        checkSession()
    }

    private fun setupView() {
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

    private fun checkSession() {
        lifecycleScope.launch {
            val userModel = viewModel.userPreference.getSession().first()
            if (userModel.token.isNotEmpty()) {
                navigateToMain()
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            this.Login(email, password)
        //viewModel.login(email, password)
        }
    }

    private fun Login( email: String, password: String) {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        val client = ApiConfig.getApiService().login( email, password)
        Log.d("tes123", client.toString())
        client.enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message.toString(), Toast.LENGTH_SHORT)
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("tes123", response.toString())
                val responseBody = response.body()
                val token = responseBody?.loginResult?.token.toString()
                Log.d("Token", token)
                if (token != null){
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@LoginActivity, responseBody?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })


    }


}
