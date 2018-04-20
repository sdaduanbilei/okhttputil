package com.scorpio.baselib.http.interceptor

/**
 * Created by scorpio on 2017/3/27.
 */

import android.content.Context
import com.scorpio.baselib.http.utils.Cookie
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences may ary, but this will work 99% of the time.
 */
class AddCookiesInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences = Cookie().getCookie(context, chain.request().url().host())
        if (preferences.isNullOrEmpty()) {
            return chain.proceed(builder.build());
        } else {
            builder.addHeader("Cookie", preferences!!)
            return chain.proceed(builder.build())
        }
    }
}