package com.me.guanpj.kotlin.ppjoke.model

import kotlinx.serialization.Serializable

@Serializable
data class Feed(var id: Int,
                var itemId: Long,
                var itemType: Int,
                var createTime: Long,
                var duration: Double,
                var feeds_text: String?,
                var authorId: Long,
                var activityIcon: String?,
                var activityText: String?,
                var width: Int,
                var height: Int,
                var url: String,
                var cover: String,
                var author: User?,
                var topComment: Comment?,
                var ugc: Ugc){
    companion object {
        const val TYPE_IMAGE_TEXT = 1 //图文
        const val TYPE_VIDEO = 2 //视频
    }
}