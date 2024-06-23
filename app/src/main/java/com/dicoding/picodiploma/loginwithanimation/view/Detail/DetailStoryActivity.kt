package com.dicoding.picodiploma.loginwithanimation.view.Detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var detailName: TextView
    private lateinit var detailPhoto: ImageView
    private lateinit var detailDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)

        detailName = findViewById(R.id.tv_detail_name)
        detailPhoto = findViewById(R.id.iv_detail_photo)
        detailDescription = findViewById(R.id.subtitle)

        val name = intent.getStringExtra("tv_detail_name")
        val photoUrl = intent.getStringExtra("iv_detail_photo")
        val description = intent.getStringExtra("tv_detail_description")

        detailName.text = name
        detailDescription.text = description
        Glide.with(this).load(photoUrl).into(detailPhoto)
    }
}
