package com.me.guanpj.kotlin.ppjoke.model

import kotlinx.serialization.Serializable

@Serializable
data class Tag(var id: Int,
               var icon: String,
               var background: String,
               var activityIcon: String,
               var title: String,
               var intro: String,
               var hasDissed: String,
               var feedNum: Int,
               var tagId: Long,
               var enterNum: Int,
               var followNum: Int,
               var hasFollow: Boolean) {
}