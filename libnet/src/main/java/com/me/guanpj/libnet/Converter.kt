package com.me.guanpj.libnet

import java.lang.reflect.Type

interface Converter<T> {
    fun convert(response: String, type: Type): T?
}