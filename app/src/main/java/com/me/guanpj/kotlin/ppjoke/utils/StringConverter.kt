package com.me.guanpj.kotlin.ppjoke.utils

object StringConverter {
    @JvmStatic
    fun convertFeedUgc(count: Int): String = if (count < 10000) count.toString() else "${count / 1000}万"
}