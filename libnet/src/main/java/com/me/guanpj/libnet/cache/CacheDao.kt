package com.me.guanpj.libnet.cache

import androidx.room.*

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(cache: Cache): Long

    @Query("select * from cache where `key`=:key")
    fun get(key: String): Cache?

    @Delete
    fun delete(cache: Cache): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cache: Cache): Int
}