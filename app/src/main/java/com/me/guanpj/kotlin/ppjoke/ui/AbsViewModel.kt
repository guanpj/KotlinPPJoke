package com.me.guanpj.kotlin.ppjoke.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

abstract class AbsViewModel<T> : ViewModel() {
    val dataSource by lazy { createDataSource() }

    val config by lazy {
        PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(12)
            //.setMaxSize(100)
            //.setEnablePlaceholders(false)
            //.setPrefetchDistance()
            .build()
    }

    val factory: DataSource.Factory<Any, T> by lazy {
        object : DataSource.Factory<Any, T>() {
            override fun create(): DataSource<Any, T> {
                return dataSource
            }
        }
    }

    val callback by lazy {
        object : PagedList.BoundaryCallback<T>() {
            override fun onZeroItemsLoaded() {
                boundaryPageData.postValue(false)
            }

            override fun onItemAtFrontLoaded(itemAtFront: T) {
                boundaryPageData.postValue(true)
            }

            override fun onItemAtEndLoaded(itemAtEnd: T) {
            }
        }
    }

    val pageData: LiveData<PagedList<T>> by lazy {
        LivePagedListBuilder(factory, config)
            .setInitialLoadKey(0)
            //.setFetchExecutor()
            .setBoundaryCallback(callback)
            .build()
    }

    val boundaryPageData by lazy {
        MutableLiveData<Boolean>()
    }


    abstract fun createDataSource() : DataSource<Any, T>
}