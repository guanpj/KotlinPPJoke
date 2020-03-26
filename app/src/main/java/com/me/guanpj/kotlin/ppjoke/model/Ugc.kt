package com.me.guanpj.kotlin.ppjoke.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.serialization.Serializable

@Serializable
data class Ugc(var likeCount: Int,
               var shareCount: Int,
               var commentCount: Int,
               var hasFavorite: Boolean,
               var hasLiked: Boolean,
               var hasdiss: Boolean) : BaseObservable() {
    var _shareCount: Int
        @Bindable get() = shareCount
        set(value) {
            shareCount = value
            //notifyPropertyChanged(BR.title)
        }

    var _hasLiked: Boolean
        @Bindable get() = hasLiked
        set(value) {
            hasLiked = value
            //notifyPropertyChanged(BR.title)
        }

    var _hasdiss: Boolean
        @Bindable get() = hasdiss
        set(value) {
            hasdiss = value
            //notifyPropertyChanged(BR.title)
        }

    var _hasFavorite: Boolean
        @Bindable get() = hasFavorite
        set(value) {
            hasFavorite = value
            //notifyPropertyChanged(BR.title)
        }
}