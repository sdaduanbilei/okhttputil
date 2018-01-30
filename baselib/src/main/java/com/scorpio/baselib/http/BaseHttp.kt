package com.scorpio.baselib.http

import com.scorpio.baselib.http.builder.GetBuilder
import com.scorpio.baselib.http.callback.Callback
import com.scorpio.baselib.http.request.RequestCall
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException


/**
 * Created by sdaduanbilei on 18-1-3.
 */
class BaseHttp {

    val DEFAULT_MILLISECONDS = 10000L
    private var mOkHttpCleint:OkHttpClient? = null


    init {
        if (this.mOkHttpCleint == null){
            this.mOkHttpCleint = OkHttpClient()

        }
    }


    fun getOkHttpClient(): OkHttpClient {
        return mOkHttpCleint!!
    }

    fun get(): GetBuilder {
        return GetBuilder()
    }

    fun execute(requestCall: RequestCall, callback: Callback<*>) {
        requestCall.getCall()!!.enqueue(object :okhttp3.Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call?, response: Response?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}