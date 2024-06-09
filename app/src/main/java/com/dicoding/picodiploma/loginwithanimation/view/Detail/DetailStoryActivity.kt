package com.dicoding.picodiploma.loginwithanimation.view.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.UserModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.UserViewModel
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val name = intent.getStringExtra("name")
        val photoUrl = intent.getStringExtra("photoUrl")
        val description = intent.getStringExtra("description")
        val pref = UserPreference.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, UserModelFactory(pref)).get(
            UserViewModel::class.java
        )

        mainViewModel.getToken().observe(this){
                token ->
            if (token == "null"){
                toLogin()
            }
            else{
                setDetailStory(photoUrl, name, description)
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setDetailStory(photoUrl: String?, name: String?, description: String?){
        Glide.with(this).load(photoUrl).into(binding.imageView)
        binding.textView.text = name
        binding.textView2.text = description
    }

    private fun toLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
