package com.richard.postsapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.richard.postsapp.util.Constants.DB_NAME

@Database(entities = [PostCacheEntity::class], version = 1)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        const val DATABASE_NAME: String = DB_NAME
    }
}