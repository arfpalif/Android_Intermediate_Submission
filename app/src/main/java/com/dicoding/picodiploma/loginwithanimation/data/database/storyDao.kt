package com.dicoding.picodiploma.loginwithanimation.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem

@Dao
interface storyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: List<ListStoryItem>)

    @Query("SELECT * FROM list_Story_item")
    fun getAllQuote(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM list_Story_item")
    suspend fun deleteAll()
}