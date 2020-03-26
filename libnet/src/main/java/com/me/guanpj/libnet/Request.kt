package com.me.guanpj.libnet

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import com.me.guanpj.libnet.cache.CacheManager
import kotlinx.serialization.Serializable
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


enum class CacheStrategy(val value: Int) {
    //仅仅只访问本地缓存，即便本地缓存不存在，也不会发起网络请求
    CACHE_ONLY(1),
    //先访问缓存，同时发起网络的请求，成功后缓存到本地
    CACHE_FIRST(2),
    //仅仅只访问服务器，不存任何存储
    NET_ONLY(3),
    //先访问网络，成功后缓存到本地
    NET_CACHE(4)
}

abstract class Request<T, R : Request<T, R>> constructor(var url: String) {

    val headers = mutableMapOf<String, String>()
    val params = mutableMapOf<String, Any>()
    var type: Type? = null
    var cacheKey: String? =  null
    var cacheStrategy: CacheStrategy? = CacheStrategy.NET_ONLY

    fun addHeader(key: String, value: String): R {
        headers[key] = value
        return this as R
    }

    fun addParam(key: String, value: Any): R {
        try {
            if (value.javaClass == String::class.java) {
                params[key] = value
            } else {
                val field: Field = value.javaClass.getField("TYPE")
                val clz = field.get(null) as Class<*>
                if (clz.isPrimitive) {
                    params[key] = value
                }
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return this as R
    }

    fun responseType(type: Type): R {
        this.type = type
        return this as R
    }

    fun cacheKey(key: String): R {
        this.cacheKey = key
        return this as R
    }

    fun cacheStrategy(cacheStrategy: CacheStrategy): R {
        this.cacheStrategy = cacheStrategy
        return this as R
    }

    @SuppressLint("RestrictedApi")
    fun execute(callback: Callback<T>) {
        if (cacheStrategy != CacheStrategy.NET_ONLY) {
            ArchTaskExecutor.getIOThreadExecutor().execute(Runnable {
                val response: ApiResponse<T> = readCache()
                if (response.body != null) {
                    callback.onCacheSuccess(response)
                }
            })
        }
        if (cacheStrategy != CacheStrategy.CACHE_ONLY) {
            getCall().enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val result = ApiResponse<T>(message = e.message)
                    callback.onError(result)
                }

                override fun onResponse(call: Call, response: Response) {
                    val result = parseResponse(response, callback)
                    if (result.success!!) callback.onSuccess(result) else callback.onError(result)
                }
            })
        }
    }

    fun execute(): ApiResponse<T>? {
        if (cacheStrategy == CacheStrategy.NET_ONLY) {
            return readCache()
        }

        var result: ApiResponse<T>? = null
        try {
            val response = getCall().execute()
            result = parseResponse(response, null)
        } catch (e: Exception) {
            e.printStackTrace()
            if (result == null) {
                result = ApiResponse(message = e.message)
            }
        }
        return result
    }

    private fun parseResponse(response: Response, callback: Callback<T>?): ApiResponse<T> {
        var message: String? = null
        var status = response.code
        var success: Boolean = response.isSuccessful
        val result = ApiResponse<T>()
        val convert = GsonConverter()
        try {
            val content = response.body!!.string()
            if (success) {
                if (callback != null) {
                    val type: ParameterizedType =
                        callback.javaClass.getGenericSuperclass() as ParameterizedType
                    val argument: Type = type.getActualTypeArguments().get(0)
                    result.body = convert.convert(content, argument) as T
                } else if (type != null) {
                    result.body = convert.convert(content, type!!) as T
                } else {
                }
            } else {
                message = content
            }
        } catch (e: java.lang.Exception) {
            message = e.message
            success = false
            status = 0
        }

        result.success = success
        result.status = status
        result.message = message

        if (cacheStrategy != CacheStrategy.NET_ONLY && result.success!! && result.body != null && result.body is Serializable) {
            saveCache(result.body!!)
        }

        return result
    }

    private fun getCall(): Call {
        val builder = okhttp3.Request.Builder();
        for ((k, v) in headers) {
            builder.addHeader(k, v)
        }
        val request = generateRequest(builder)
        return ApiService.okHttpClient.newCall(request)
    }

    private fun readCache(): ApiResponse<T> {
        val cache = run {
            if (cacheKey != null) cacheKey!! else generateCacheKey()!!
        }.let {
            CacheManager.get(it)
        }

        return ApiResponse<T>().apply {
            status = 304
            message = "缓存获取成功"
            body = cache as T
            success = true
        }
    }

    private fun saveCache(body: T) {
        run {
            if (cacheKey != null) cacheKey!! else generateCacheKey()!!
        }.let {
            CacheManager.inserOrReplace(it, body)
        }
    }

    private fun generateCacheKey(): String? = UrlCreator.createUrlFromParams(url, params)

    protected abstract fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request

}