package com.scorpio.baselib.http.request

import com.scorpio.baselib.http.HttpLogger
import com.scorpio.baselib.http.OkHttpUtils
import com.scorpio.baselib.http.callback.Callback
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * Created by sdaduanbilei on 18-1-3.
 */
class RequestCall(request: OkHttpRequest) {
    private val TAG = "RequestCall"
    private var okHttpRequest: OkHttpRequest = request
    private var request: Request? = null
    private var call: Call? = null

    private var readTimeOut: Long = 0
    private var writeTimeOut: Long = 0
    private var connTimeOut: Long = 0

    private var client: OkHttpClient? = null


    fun readTimeOut(readTimeOut: Long): RequestCall {
        this.readTimeOut = readTimeOut
        return this
    }

    fun writeTimeOut(writeTimeOut: Long): RequestCall {
        this.writeTimeOut = writeTimeOut
        return this
    }

    fun connTimeOut(connTimeOut: Long): RequestCall {
        this.connTimeOut = connTimeOut
        return this
    }


    private fun buildCall(callback: Callback<*>?): Call? {
        request = generateRequest(callback!!)
        val loggingInterceptor = HttpLoggingInterceptor(HttpLogger())
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {
            readTimeOut = if (readTimeOut > 0) readTimeOut else OkHttpUtils.DEFAULT_MILLISECONDS
            writeTimeOut = if (writeTimeOut > 0) writeTimeOut else OkHttpUtils.DEFAULT_MILLISECONDS
            connTimeOut = if (connTimeOut > 0) connTimeOut else OkHttpUtils.DEFAULT_MILLISECONDS

            // 请求设置
            client = OkHttpUtils().getOkHttpClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(loggingInterceptor)
                    .build()
            call = client!!.newCall(request!!)
        } else {
            client = OkHttpUtils().getOkHttpClient().newBuilder()
                    .addNetworkInterceptor(loggingInterceptor)
                    .build()
            call = client!!.newCall(request!!)
        }

        return call
    }

    private fun generateRequest(callback: Callback<*>): Request {
        return okHttpRequest.generateRequest(callback)
    }

    /**
     * 异步请求callback
     */
    fun execute(callback: Callback<*>) {
        buildCall(callback)
        callback.onBefore(request!!,getOkHttpRequest()!!.getId())
        OkHttpUtils().execute(this,callback)
    }

    fun execute(): Response? {
        buildCall(null)
        return call!!.execute()
    }

    fun getCall(): Call? {
        return call
    }

    fun getRequest(): Request? {
        return request
    }

    fun getOkHttpRequest(): OkHttpRequest? {
        return okHttpRequest
    }

    fun cancel() {
        if (call != null) {
            call!!.cancel()
        }
    }


}