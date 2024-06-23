package com.dicoding.picodiploma.loginwithanimation.view.Home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.data.response.GetStoryResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityHomeBinding
import com.dicoding.picodiploma.loginwithanimation.view.StoryViewModel
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.home.HomeAdapter
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel
import com.dicoding.picodiploma.loginwithanimation.view.uploadStory.UploadStoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.clear()
        ViewModelFactory.getInstance(this)
    }
    private val storyViewModel by viewModels<StoryViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityHomeBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference.getInstance(dataStore)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                showList(user.token)
            }
        }

        setupView()
        setupAction()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSession().observe(this) { user ->
            showList(user.token)
        }
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

    private fun setupAction() {
        binding.appBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.logout_menu -> {
                    viewModel.logout()
                    true
                }
                else -> false
            }
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, UploadStoryActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun showList(token: String) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvItem.layoutManager = layoutManager

        val client = ApiConfig.getApiService(token).getStories()
        client.enqueue(object : Callback<GetStoryResponse> {
            override fun onResponse(
                call: Call<GetStoryResponse>,
                response: Response<GetStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        loadStory()
                    } else {
                        Toast.makeText(this@HomeActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@HomeActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetStoryResponse>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        val adapter = HomeAdapter()
        storyViewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun loadStory() {
        val adapter = HomeAdapter()
        binding.rvItem.adapter = adapter
        storyViewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}
