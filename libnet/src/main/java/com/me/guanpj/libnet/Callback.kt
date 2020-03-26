package com.me.guanpj.libnet

abstract class Callback<T> {
    open fun onSuccess(response: ApiResponse<T>) {}

    open fun onError(response: ApiResponse<T>) {}

    open fun onCacheSuccess(response: ApiResponse<T>) {}
}