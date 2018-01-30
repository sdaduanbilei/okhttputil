package com.scorpio.baselib.http.request

import okhttp3.Callback
import okhttp3.Headers
import okhttp3.Request
import okhttp3.RequestBody


/**
 * Created by sdaduanbilei on 18-1-3.
 */
abstract class OkHttpRequest(private var url:String, private var tag: Any, private var params: Map<String, String>, private var headers: Map<String, String>, private var id: Int) {


    protected var builder = Request.Builder()

    init {
        initBuilder()
    }
    /**
     * 初始化一些基本参数 url , tag , headers
     */
    private fun initBuilder() {
        builder.url(url).tag(tag)
        appendHeaders()
    }

    protected abstract fun buildRequestBody(): RequestBody?

    private fun wrapRequestBody(requestBody: RequestBody?, callback: Callback): RequestBody? {
        return requestBody
    }

    protected abstract fun buildRequest(requestBody: RequestBody?): Request

    fun build(): RequestCall {
        return RequestCall(this)
    }


    fun generateRequest(callback: Callback): Request {
        val requestBody = buildRequestBody()
        val wrappedRequestBody = wrapRequestBody(requestBody, callback)
        return buildRequest(wrappedRequestBody)
    }


    /**
     * 添加header
     */
    private fun appendHeaders() {
        val headerBuilder = Headers.Builder()
        if (headers.isEmpty()) return

        for (key in headers.keys) {
            headerBuilder.add(key, headers[key])
        }
        builder.headers(headerBuilder.build())
    }

    fun getId():Int{
        return id
    }
}