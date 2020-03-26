package com.me.guanpj.libnet

data class ApiResponse<T> (var success: Boolean? = null,
                           var status: Int? = null,
                           var message: String? = null,
                           var body: T? = null)