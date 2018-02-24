package com.scorpio.baselib.http.builder


import android.net.Uri
import com.scorpio.baselib.http.request.GetRequest
import com.scorpio.baselib.http.request.RequestCall

/**
 * Created by zhy on 15/12/14.
 */
class GetBuilder : OkHttpRequestBuilder<GetBuilder>(), HasParamsable {

    private val TAG: String = "GetBuilder"

    override fun build(): RequestCall {
        if(url.isEmpty()){
            Throwable("url can not be null .")
        }
        if (this.params.isNotEmpty()) {
            url = this.appendParams(this.url, this.params).toString()
        }
        return GetRequest(url, this.tag, this.params, this.headers, id).build()
    }

    /**
     * 构建参数
     */
    private fun appendParams(url: String?, params: Map<String, String>?): String? {
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


    /**
     * 添加params
     */
    override fun params(params: MutableMap<String, String>): GetBuilder {
        if (this.params.isEmpty()) {
            this.params = params
        } else {
            this.params.putAll(params)
        }
        return this
    }

    /**
     * 添加单一param
     */
    override fun param(key: String, `val`: String): GetBuilder {
        if (this.params.isEmpty()) {
            this.params = HashMap()
        }
        this.params[key] = `val`
        return this
    }
}
