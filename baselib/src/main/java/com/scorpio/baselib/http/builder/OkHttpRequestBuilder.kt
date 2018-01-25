package com.scorpio.baselib.http.builder


import com.scorpio.baselib.http.request.RequestCall
import java.util.LinkedHashMap

/**
 * Created by zhy on 15/12/14.
 */
abstract class OkHttpRequestBuilder<Self: OkHttpRequestBuilder<Self>> {
    protected var url: String = ""
    protected var tag: Any = System.currentTimeMillis()
    protected var headers: MutableMap<String, String> = HashMap<String,String>()
    protected var params: MutableMap<String, String> = HashMap<String,String>()
    protected var id: Int = 0

    fun id(id: Int): Self {
        this.id = id
        return this as Self
    }

    fun url(url: String): Self {
        this.url = url
        return this as Self
    }


    fun tag(tag: Any): Self {
        this.tag = tag
        return this as Self
    }

    fun headers(headers: MutableMap<String, String>): Self {
        this.headers = headers
        return this as Self
    }

    fun addHeader(key: String, `val`: String): Self {
        if (this.headers == null) {
            headers = LinkedHashMap()
        }
        headers!!.put(key, `val`)
        return this as Self
    }

    abstract fun build(): RequestCall
}
