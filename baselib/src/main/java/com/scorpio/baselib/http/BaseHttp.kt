package com.scorpio.baselib.http

import okhttp3.OkHttpClient
import com.scorpio.baselib.http.builder.GetBuilder



/**
 * Created by sdaduanbilei on 18-1-3.
 */
object BaseHttp {

    val DEFAULT_MILLISECONDS = 10000L
    private var mOkHttpCleint:OkHttpClient? =null
    fun getInstance(): BaseHttp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getOkHttpClient(): OkHttpClient {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun get(): GetBuilder {
        return GetBuilder()
    }
}