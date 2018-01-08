package com.scorpio.baselib.http.builder


import java.util.LinkedHashMap

/**
 * Created by zhy on 15/12/14.
 */
abstract class OkHttpRequestBuilder<Self: OkHttpRequestBuilder<Self>> {
    protected var url: String = ""
    protected var tag: Any? = null
    protected var headers: MutableMap<String, String>? = null
    protected var params: MutableMap<String, String>? = null
    protected var id: Int = 0

    fun id(id: Int): OkHttpRequestBuilder<Self> {
        this.id = id
        return this
    }

    fun url(url: String): OkHttpRequestBuilder<Self> {
        this.url = url
        return this
    }


    fun tag(tag: Any): OkHttpRequestBuilder<Self> {
        this.tag = tag
        return this
    }

    fun headers(headers: MutableMap<String, String>): OkHttpRequestBuilder<Self> {
        this.headers = headers
        return this
    }

    fun addHeader(key: String, `val`: String): OkHttpRequestBuilder<Self> {
        if (this.headers == null) {
            headers = LinkedHashMap()
        }
        headers!!.put(key, `val`)
        return this
    }

//    abstract fun build(): RequestCall
}
