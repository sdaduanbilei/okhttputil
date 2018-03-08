package com.scorpio.drive

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by scorpio on 2018/3/8.
 */
class HeaderInterceptor :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("City", "530102")
        builder.addHeader("Platform","Android/91")
        builder.addHeader("Authorization","ASDFADAFASDFE8802302")
        return chain.proceed(builder.build())

    }
}