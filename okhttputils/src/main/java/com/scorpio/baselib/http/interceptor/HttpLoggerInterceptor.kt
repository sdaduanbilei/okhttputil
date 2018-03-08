package com.scorpio.baselib.http.interceptor

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by scorpio on 2018/1/30.
 */
class HttpLoggerInterceptor :HttpLoggingInterceptor.Logger{
    override fun log(message: String?) {
        Log.e("okhttp",message)
    }
}