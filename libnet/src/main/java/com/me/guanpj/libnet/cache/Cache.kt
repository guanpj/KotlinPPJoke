package com.me.guanpj.libnet.cache

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "cache")
data class Cache(
    @PrimaryKey
    @NonNull
    var key: String, var data: ByteArray)