package com.scorpio.baselib.http.request

import android.os.AsyncTask.execute
import com.scorpio.baselib.http.BaseHttp
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by sdaduanbilei on 18-1-3.
 */
class RequestCall(okHttpRequest: OkHttpRequest) {
    private var okHttpRequest: OkHttpRequest? = null
    private var request: Request? = null
    private var call: Call? = null

    private var readTimeOut: Long = 0
    private var writeTimeOut: Long = 0
    private var connTimeOut: Long = 0

    private var client: OkHttpClient? = null

    fun RequestCall(request: OkHttpRequest){
        this.okHttpRequest = request
    }

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

    fun buildCall(callback: Callback?): Call? {
        request = generateRequest(callback)

        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {
            readTimeOut = if (readTimeOut > 0) readTimeOut else BaseHttp.DEFAULT_MILLISECONDS
            writeTimeOut = if (writeTimeOut > 0) writeTimeOut else BaseHttp.DEFAULT_MILLISECONDS
            connTimeOut = if (connTimeOut > 0) connTimeOut else BaseHttp.DEFAULT_MILLISECONDS

            client = BaseHttp.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                    .build()

            call = client!!.newCall(request!!)
        } else {
            call = BaseHttp.getInstance().getOkHttpClient().newCall(request)
        }
        return call
    }

    private fun generateRequest(callback: Callback?): Request {
        return okHttpRequest!!.generateRequest(callback!!)
    }

    fun execute(callback: Callback?) {
        buildCall(callback)

        if (callback != null) {
//            callback!!.onBefore(request, getOkHttpRequest().getId())
        }

//        OkHttpUtils.getInstance().execute(this, callback)
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

    @Throws(IOException::class)
    fun execute(): Response {
        buildCall(null)
        return call!!.execute()
    }

    fun cancel() {
        if (call != null) {
            call!!.cancel()
        }
    }
}