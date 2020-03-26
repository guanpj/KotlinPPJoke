package com.me.guanpj.kotlin.ppjoke.model

data class Comment(var id: Int,
                   var itemId: Long,
                   var commentId: Long,
                   var userId: Long,
                   var commentType: Int,
                   var createTime: Long,
                   var commentCount: Int,
                   var likeCount: Int,
                   var commentText: String,
                   var imageUrl: String,
                   var videoUrl: String,
                   var width: Int,
                   var height: Int,
                   var hasLiked: Boolean,
                   var author: User,
                   var ugc: Ugc?){

}