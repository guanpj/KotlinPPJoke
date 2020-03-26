package com.me.guanpj.libnet

import okhttp3.FormBody

class PostRequest<T> constructor(var requestUrl: String) : Request<T, PostRequest<T>>(requestUrl) {
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val bodyBuilder = FormBody.Builder()
        for ((key, value) in params) {
            bodyBuilder.add(key, value.toString())
        }
        return builder.url(url).post(bodyBuilder.build()).build()
    }
}