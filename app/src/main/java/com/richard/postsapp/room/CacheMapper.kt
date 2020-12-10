package com.richard.postsapp.room

import com.richard.postsapp.data.Post
import com.richard.postsapp.util.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor(): EntityMapper<PostCacheEntity, Post> {

    override fun mapFromEntity(entity: PostCacheEntity): Post {
        return Post(
            userId = entity.userId,
            id = entity.id,
            title = entity.title,
            body = entity.body
        )
    }

    override fun mapToEntity(domainModel: Post): PostCacheEntity {
        return PostCacheEntity(
            userId = domainModel.userId,
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body
        )
    }

    fun mapFromEntityList(entities: List<PostCacheEntity>): List<Post> {
        return entities.map {
            mapFromEntity(it)
        }
    }

}