package com.me.guanpj.libnet.cache

import androidx.room.Room
import androidx.room.RoomDatabase
import com.me.guanpj.libcommon.AppGlobals

abstract class CacheDatabase : RoomDatabase() {
    companion object {
        val instance: CacheDatabase by lazy {
            Room.databaseBuilder(AppGlobals.application, CacheDatabase::class.java, "ppjoke_cache")
                .allowMainThreadQueries().build()
        }
    }

    abstract fun getCacheDao(): CacheDao
}