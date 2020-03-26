package com.me.guanpj.kotlin.ppjoke.utils

import android.content.Context
import android.util.DisplayMetrics

object ScreenUtils {
    fun dp2Px(context: Context, dpValue: Int): Int {
        val metrics: DisplayMetrics = context.resources.displayMetrics
        return (metrics.density * dpValue + 0.5f).toInt()
    }
}
