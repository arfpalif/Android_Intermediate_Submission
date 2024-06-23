package com.dicoding.picodiploma.loginwithanimation.view.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.view.Detail.DetailStoryActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

class HomeAdapter : PagingDataAdapter<ListStoryItem, HomeAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_story, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailStoryActivity::class.java).apply {
                    putExtra("tv_detail_name", user.name)
                    putExtra("iv_detail_photo", user.photoUrl)
                    putExtra("tv_detail_description", user.description)
                }
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        holder.itemView.context as Activity,
                        Pair(holder.imgStory, "storyImage"),
                        Pair(holder.storyTitle, "storyTitle"),
                        Pair(holder.storyDesc, "storyDesc")
                    )
                holder.itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val storyTitle: TextView = view.findViewById(R.id.tv_item_name)
        val storyDesc: TextView = view.findViewById(R.id.subtitle)
        val imgStory: ImageView = view.findViewById(R.id.iv_item_photo)

        fun bind(user: ListStoryItem) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = dateFormat.parse(user.createdAt)
            val formattedDate = DateFormat.getDateInstance(DateFormat.FULL, Locale("ID"))
            val dateStr = formattedDate.format(date)
            storyTitle.text = user.name
            storyDesc.text = dateStr
            Glide.with(itemView.context).load(user.photoUrl).into(imgStory)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
