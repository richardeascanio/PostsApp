package com.richard.postsapp.retrofit

import com.richard.postsapp.data.Post
import com.richard.postsapp.room.PostCacheEntity
import com.richard.postsapp.util.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor(): EntityMapper<PostNetworkEntity, Post> {

    override fun mapFromEntity(entity: PostNetworkEntity): Post {
        return Post(
            userId = entity.userId,
            id = entity.id,
            title = entity.title,
            body = entity.body
        )
    }

    override fun mapToEntity(domainModel: Post): PostNetworkEntity {
        return PostNetworkEntity(
            userId = domainModel.userId,
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body
        )
    }

    fun mapFromEntityList(entities: List<PostNetworkEntity>): List<Post> {
        return entities.map {
            mapFromEntity(it)
        }
    }
}