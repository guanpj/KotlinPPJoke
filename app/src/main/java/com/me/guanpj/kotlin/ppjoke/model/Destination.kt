package com.me.guanpj.kotlin.ppjoke.model

data class Destination(var id: Int,
                       var pageUrl: String,
                       var className: String,
                       var needLogin: Boolean,
                       var asStarter: Boolean,
                       var isFragment: Boolean)