package com.richard.postsapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(postEntity: PostCacheEntity): Long

    @Query("SELECT * FROM post_table")
    suspend fun get(): List<PostCacheEntity>
}