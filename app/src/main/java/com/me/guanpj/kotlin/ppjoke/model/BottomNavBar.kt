package com.me.guanpj.kotlin.ppjoke.model

data class BottomNavBar(var activeColor: String,
                        var inActiveColor: String,
                        var tabs: ArrayList<Tabs>,
                        var selectedTab: Int)

data class Tabs(var size: Int,
                var enable: Boolean,
                var index: Int,
                var pageUrl: String,
                var title: String,
                var tintColor: String)
