package com.scorpio.baselib.http.builder

import android.net.Uri
import android.text.TextUtils
import com.scorpio.baselib.http.request.GetRequest
import com.scorpio.baselib.http.request.OkHttpRequestBuilder
import com.scorpio.baselib.http.request.RequestCall

/**
 * Created by sdaduanbilei on 18-1-3.
 */
abstract class GetBuilder<*> : OkHttpRequestBuilder<GetBuilder<Any?>,*>(), HasParamsable {
    override fun params(params: MutableMap<String, String>): GetBuilder<*> {
        this.params = params
        return this
    }

    override fun param(key: String, `val`: String): GetBuilder<*> {
        if (this.params == null) {
            this.params = HashMap()
        }
        this.params!!.put(key,`val`)
        return this
    }

//    override fun params(params: HashMap<String, String>): GetBuilder<*> {
//        this.params = params
//        return this
//    }
//
//    override fun param(key: String, `val`: String): GetBuilder<*> {
//        if (this.params == null) {
//            this.params = LinkedHashMap()
//        }
//        this.params!!.(key, `val`)
//        return this
//    }


    /**
     * 构建请求体
     */
    fun build(): RequestCall {
        if (this.params!=null) {
            this.url = this.appendParams(this.url, this.params!!)
        }
        return GetRequest(this.url, this.tag!!, this.params!!, this.headers!!, this.id).build()
    }

    /**
     * build params
     */
    private fun appendParams(url: String, params: Map<String, String>): String {
        if (!TextUtils.isEmpty(url) && !params.isEmpty()) {
            val builder = Uri.parse(url).buildUpon()
            val keys = params.keys
            val iterator = keys.iterator()
            while (iterator.hasNext()) {
                val key = iterator.next()
                builder.appendQueryParameter(key, params[key])
            }
            return builder.build().toString()
        } else {
            return url
        }
    }




}