package com.me.guanpj.libnet.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object CacheManager {
    fun <T> inserOrReplace(key: String, body: T) {
        toByteArray(body)?.let {
            Cache(key, it)
        }?.let {
            CacheDatabase.instance.getCacheDao().insertOrReplace(it)
        }
    }

    fun get(key: String): Any? {
        return CacheDatabase.instance.getCacheDao().get(key)?.let {
            toObject(it.data)
        }
    }

    fun <T> delete(key: String, body: T) {
        toByteArray(body)?.let {
            Cache(key, it)
        }?.let {
            CacheDatabase.instance.getCacheDao().delete(it)
        }
    }

    private fun <T> toByteArray(body: T): ByteArray? {
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(body)
            oos.flush()
            return baos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                baos?.close()
                oos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return ByteArray(0)
    }

    private fun toObject(data: ByteArray): Any? {
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        try {
            bais = ByteArrayInputStream(data)
            ois = ObjectInputStream(bais)
            return ois.readObject()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                bais?.close()
                ois?.close()
            } catch (ignore: java.lang.Exception) {
                ignore.printStackTrace()
            }
        }
        return null
    }
}