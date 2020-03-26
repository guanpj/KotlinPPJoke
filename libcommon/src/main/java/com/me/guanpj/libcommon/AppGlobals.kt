package com.me.guanpj.libcommon

import android.annotation.SuppressLint
import android.app.Application

@SuppressLint("DiscouragedPrivateApi", "PrivateApi")
object AppGlobals {
    val application: Application by lazy {
        val method = Class.forName("android.app.ActivityThread")
            .getDeclaredMethod("currentApplication")
        method.invoke(null) as Application
    }
}