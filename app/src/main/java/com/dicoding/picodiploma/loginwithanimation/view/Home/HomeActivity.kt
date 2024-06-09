package com.dicoding.picodiploma.loginwithanimation.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityHomeBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.view.StoryViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val factory = ViewModelFactory.getInstance(this)
        val storyViewModel = ViewModelProvider(this, factory).get(StoryViewModel::class.java)

        storyViewModel.stories.observe(this) { stories: List<ListStoryItem> ->
            homeAdapter.setStories(stories)
        }

        setSupportActionBar(binding.toolbar)
    }

    private fun setupRecyclerView() {
        homeAdapter = HomeAdapter()
        binding.rvItem.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = homeAdapter
        }
    }
}
