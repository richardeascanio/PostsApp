package com.richard.postsapp.di

import com.richard.postsapp.repository.MainRepository
import com.richard.postsapp.retrofit.NetworkMapper
import com.richard.postsapp.retrofit.PostRetrofit
import com.richard.postsapp.room.CacheMapper
import com.richard.postsapp.room.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        postDao: PostDao,
        retrofit: PostRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(postDao, retrofit, cacheMapper, networkMapper)
    }

}