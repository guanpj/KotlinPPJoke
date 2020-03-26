package com.me.guanpj.libnet

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class GetRequest<T> constructor(var requestUrl: String) : Request<T, GetRequest<T>>(requestUrl) {
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val url: String = UrlCreator.createUrlFromParams(url, params)
        return builder.get().url(url).build()
    }
}