package com.me.guanpj.libcommon.util

import com.me.guanpj.libcommon.AppGlobals.application


object PixelUtils {
    fun dp2px(dpValue: Int): Int {
        val metrics = application.resources.displayMetrics
        return (metrics.density * dpValue + 0.5f).toInt()
    }

    fun getScreenWidth(): Int {
        val metrics = application.resources.displayMetrics
        return metrics.widthPixels
    }

    fun getScreenHeight(): Int {
        val metrics = application.resources.displayMetrics
        return metrics.heightPixels
    }
}