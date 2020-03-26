package com.me.guanpj.kotlin.ppjoke.model

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int,
                var userId: Long,
                var name: String,
                var avatar: String,
                var description: String?,
                var likeCount: Int,
                var topCommentCount: Int,
                var followCount: Int,
                var followerCount: Int,
                var qqOpenId: String,
                var expires_time: Long,
                var score: Int,
                var historyCount: Int,
                var commentCount: Int,
                var favoriteCount: Int,
                var feedCount: Int,
                var hasFollow: Boolean)