package com.scorpio.baselib.http.request


import com.scorpio.baselib.http.request.RequestCall
import java.util.LinkedHashMap

/**
 * Created by zhy on 15/12/14.
 */
abstract class OkHttpRequestBuilder<Self: OkHttpRequestBuilder<Self, T>, T> {
    protected var url: String = ""
    protected var tag: Any? = null
    protected var headers: MutableMap<String, String>? = null
    protected var params: MutableMap<String, String>? = null
    protected var id: Int = 0

    fun id(id: Int): T {
        this.id = id
        return this as T
    }

    fun url(url: String): T {
        this.url = url
        return this as T
    }


    fun tag(tag: Any): T {
        this.tag = tag
        return this as T
    }

    fun headers(headers: MutableMap<String, String>): T {
        this.headers = headers
        return this as T
    }

    fun addHeader(key: String, `val`: String): T {
        if (this.headers == null) {
            headers = LinkedHashMap()
        }
        headers!!.put(key, `val`)
        return this as T
    }

//    abstract fun build(): RequestCall
}
