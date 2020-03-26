package com.me.guanpj.kotlin.ppjoke

import android.app.Application
import com.me.guanpj.libnet.ApiService

class JApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiService.init("http://123.56.232.18:8080/serverdemo");
    }
}