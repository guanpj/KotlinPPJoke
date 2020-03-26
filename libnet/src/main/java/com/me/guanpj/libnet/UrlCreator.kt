package com.me.guanpj.libnet

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object UrlCreator {
    fun createUrlFromParams(url: String, params: Map<String, Any>): String {
        val builder = StringBuilder()
        builder.append(url)
        if (url.indexOf("?") > 0 || url.indexOf("&") > 0) {
            builder.append("&")
        } else {
            builder.append("?")
        }
        for ((key, value1) in params) {
            try {
                val value: String = URLEncoder.encode(value1.toString(), "UTF-8")
                builder.append(key).append("=").append(value).append("&")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
        builder.deleteCharAt(builder.length - 1)
        return builder.toString()
    }
}