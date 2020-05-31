package com.me.guanpj.kotlin.ppjoke.utils

import com.google.gson.Gson

inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)