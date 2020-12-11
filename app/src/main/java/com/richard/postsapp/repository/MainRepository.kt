package com.richard.postsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.richard.postsapp.data.Post
import com.richard.postsapp.retrofit.NetworkMapper
import com.richard.postsapp.retrofit.PostNetworkEntity
import com.richard.postsapp.retrofit.PostRetrofit
import com.richard.postsapp.room.CacheMapper
import com.richard.postsapp.room.PostDao
import com.richard.postsapp.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainRepository
constructor(
        private val postDao: PostDao,
        private val postRetrofit: PostRetrofit,
        private val cacheMapper: CacheMapper,
        private val networkMapper: NetworkMapper
) {

    suspend fun getPost(): Flow<DataState<List<Post>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkPosts = postRetrofit.get()
            val posts = networkMapper.mapFromEntityList(networkPosts)

            for (post in posts) {
                postDao.insert(cacheMapper.mapToEntity(post))
            }

            val cachedPosts = postDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedPosts)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getAllPosts(): List<Post>? {
        return try {
            val networkPosts = postRetrofit.get()
            val posts = networkMapper.mapFromEntityList(networkPosts)

            for (post in posts) {
                postDao.insert(cacheMapper.mapToEntity(post))
            }
            val cachedPosts = postDao.get()
            posts
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun insertPost(post: Post): Post? {
        return try {
            val postEntity = networkMapper.mapToEntity(post)
            val newPost = postRetrofit.add(postEntity)
            val postObject = networkMapper.mapFromEntity(newPost)
            val cachedPost = cacheMapper.mapToEntity(postObject)
            postDao.insert(cachedPost)

            postObject
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}