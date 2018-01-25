package com.scorpio.baselib.http.builder

import android.net.Uri
import android.util.Log
import com.scorpio.baselib.http.request.GetRequest


import com.scorpio.baselib.http.request.RequestCall
import java.util.LinkedHashMap

/**
 * Created by zhy on 15/12/14.
 */
class GetBuilder : OkHttpRequestBuilder<GetBuilder>(), HasParamsable {

    private val TAG: String = "GetBuilder"

    override fun build(): RequestCall {
        if (this.params != null) {
            url = this.appendParams(this.url, this.params).toString()
        }
        return GetRequest(url, this.tag, this.params, this.headers, id).build()
    }

    protected fun appendParams(url: String?, params: Map<String, String>?): String? {
        if (url == null || params == null || params.isEmpty()) {
            return url
        }
        val builder = Uri.parse(url).buildUpon()
        val keys = params.keys
        val iterator = keys.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            builder.appendQueryParameter(key, params[key])
        }
        return builder.build().toString()
    }


    override fun params(params: MutableMap<String, String>): GetBuilder {
        if (this.params == null) {
            this.params = params
        }else{
            this.params!!.putAll(params)
        }
        return this
    }

    override fun param(key: String, `val`: String): GetBuilder {
        if (this.params == null) {
            this.params = HashMap()
        }
        this.params!!.put(key, `val`)
        return this
    }
}
