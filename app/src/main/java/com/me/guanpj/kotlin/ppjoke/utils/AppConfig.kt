package com.me.guanpj.kotlin.ppjoke.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.me.guanpj.kotlin.ppjoke.model.BottomNavBar
import com.me.guanpj.kotlin.ppjoke.model.Destination
import com.me.guanpj.libcommon.AppGlobals

object AppConfig {
    val destConfig: HashMap<String, Destination> by lazy {
        val content = parseFile("destination.json")
        val type = object: TypeToken<HashMap<String, Destination>>(){}.type
        Gson().fromJson<HashMap<String, Destination>>(content, type)
    }

    val bottomNavBarConfig: BottomNavBar by lazy {
        val content = parseFile("main_tabs_config.json")
        Gson().fromJson<BottomNavBar>(content)
    }

    fun parseFile(fileName: String): String =
        AppGlobals.application.assets.open(fileName).bufferedReader().run {
            this.readText()
        }
}