package com.me.guanpj.libnet

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.lang.reflect.Type

class GsonConverter: Converter<Any?> {
    override fun convert(response: String, type: Type): Any? {
        val jsonObject = Gson().fromJson(response, JsonObject::class.java)
        val data = jsonObject.getAsJsonObject("data")
        if (data != null) {
            return Gson().fromJson(data.get("data").toString(), type)
        }
        return null
    }
}