package com.scorpio.baselib.http.interceptor

/**
 * Created by scorpio on 2017/3/27.
 */

import android.content.Context
import android.text.TextUtils
import com.scorpio.baselib.http.utils.Cookie
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ReceivedCookiesInterceptor(private val context: Context) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // Set-Cookie http reponse addheader
        val originalResponse = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            for (header in originalResponse.headers("Set-Cookie")) {
                val kvs = header.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (kv in kvs) {
                    if (!TextUtils.isEmpty(kv) && kv.contains("=")) {
                        val values = kv.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        for (i in values.indices) {
                            if (values.size > 1) {
                                Cookie().setCookie(context,chain.request().url().host(),kv)
                            }
                        }
                    }
                }
            }
        }
        return originalResponse
    }
}