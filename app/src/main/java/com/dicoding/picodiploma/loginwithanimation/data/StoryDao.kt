package com.dicoding.picodiploma.loginwithanimation.data

import com.dicoding.picodiploma.loginwithanimation.data.api.StoriesEntity
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StoryDao {

    @Query("SELECT * FROM stories")
    fun getStories(): LiveData<List<StoriesEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStories(stories: StoriesEntity)

    @Update
    fun updateStories(stories: StoriesEntity)

    @Query("DELETE FROM stories WHERE id IS NOT NULL")
    fun deleteAll()
}