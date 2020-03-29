package com.me.guanpj.kotlin.ppjoke.ui.home

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.google.gson.reflect.TypeToken
import com.me.guanpj.kotlin.ppjoke.model.Feed
import com.me.guanpj.kotlin.ppjoke.ui.AbsViewModel
import com.me.guanpj.libnet.*
import java.util.*


class HomeViewModel : AbsViewModel<Feed>() {

    @Volatile
    var withCache = false

    fun loadData(key: Int, callback: ItemKeyedDataSource.LoadCallback<Feed>) {
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
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun createDataSource(): DataSource<Any, Feed> = object : ItemKeyedDataSource<Any, Feed>() {
        override fun loadInitial(params: LoadInitialParams<Any>, callback: LoadInitialCallback<Feed>) {
            withCache = false
            loadData(0, callback)
        }

        override fun loadAfter(params: LoadParams<Any>, callback: LoadCallback<Feed>) {
            loadData(params.key as Int, callback)
        }

        override fun loadBefore(params: LoadParams<Any>, callback: LoadCallback<Feed>) {
            callback.onResult(Collections.emptyList())
        }

        override fun getKey(item: Feed): Any  = item.id
    }
}