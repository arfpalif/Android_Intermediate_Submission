package com.dicoding.picodiploma.loginwithanimation.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.StoryViewHolder>() {

    private val listStory = ArrayList<ListStoryItem>()

    fun setStories(stories: List<ListStoryItem>) {
        listStory.clear()
        listStory.addAll(stories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItemPhoto: ImageView = itemView.findViewById(R.id.iv_item_photo)
        private val storyTitle: TextView = itemView.findViewById(R.id.Story_title)
        private val subtitle: TextView = itemView.findViewById(R.id.subtitle)

        fun bind(story: ListStoryItem) {
            storyTitle.text = story.name
            subtitle.text = story.description
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .into(ivItemPhoto)
        }
    }
}
