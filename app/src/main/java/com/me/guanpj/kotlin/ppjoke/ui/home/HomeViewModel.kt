package com.me.guanpj.kotlin.ppjoke.ui.home

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import com.google.gson.reflect.TypeToken
import com.me.guanpj.kotlin.ppjoke.model.Feed
import com.me.guanpj.kotlin.ppjoke.ui.AbsViewModel
import com.me.guanpj.libnet.*
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


class HomeViewModel : AbsViewModel<Feed>() {

    @Volatile
    var withCache = false
    private val cacheLiveData = MutableLiveData<PagedList<Feed>>()
    private val loadAfter = AtomicBoolean(false)

    fun getCacheLiveData(): MutableLiveData<PagedList<Feed>> {
        return cacheLiveData
    }

    fun loadData(key: Int, count: Int, callback: ItemKeyedDataSource.LoadCallback<Feed>) {
        if (key > 0) {
            loadAfter.set(true);
        }
        val request = ApiService.getRequest<List<Feed>>("/feeds/queryHotFeedsList")
            .addParam("feedType", "")
            .addParam("userId", 0)
            .addParam("feedId", key)
            .addParam("pageCount", 10)
            .responseType(object: TypeToken<List<Feed>>(){}.type)

        if (withCache) {
            request.cacheStrategy(CacheStrategy.NET_ONLY)
            request.execute(object : Callback<List<Feed>>() {
                override fun onCacheSuccess(response: ApiResponse<List<Feed>>) {
                }
            })
        }


        try {
            val netRequest: GetRequest<List<Feed>> = request
            netRequest.cacheStrategy(if (key === 0) CacheStrategy.NET_CACHE else CacheStrategy.NET_ONLY)

            val response = netRequest.execute()
            val data: List<Feed>? = if (response?.body == null) Collections.emptyList() else response.body
            data?.let {
                callback.onResult(it)
                if (key > 0) {
                    boundaryPageData.postValue(it.isNotEmpty())
                    loadAfter.set(false);
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("RestrictedApi")
    fun loadAfter(id: Int, callback: ItemKeyedDataSource.LoadCallback<Feed>) {
        if (loadAfter.get()) {
            callback.onResult(Collections.emptyList())
            return
        }
        ArchTaskExecutor.getIOThreadExecutor().execute { loadData(id, config.pageSize, callback) }
    }

    override fun createDataSource(): DataSource<Any, Feed> = object : ItemKeyedDataSource<Any, Feed>() {
        override fun loadInitial(params: LoadInitialParams<Any>, callback: LoadInitialCallback<Feed>) {
            withCache = false
            loadData(0, params.requestedLoadSize, callback)
        }

        override fun loadAfter(params: LoadParams<Any>, callback: LoadCallback<Feed>) {
            loadData(params.key as Int, params.requestedLoadSize, callback)
        }

        override fun loadBefore(params: LoadParams<Any>, callback: LoadCallback<Feed>) {
            callback.onResult(Collections.emptyList())
        }

        override fun getKey(item: Feed): Any  = item.id
    }
}