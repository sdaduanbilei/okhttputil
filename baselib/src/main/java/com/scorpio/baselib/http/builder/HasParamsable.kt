package com.scorpio.baselib.http.builder

/**
 * Created by zhy on 16/3/1.
 */
interface HasParamsable {
    /**
     *  通过hashmap 添加params
     */
    fun params(params: MutableMap<String, String>): OkHttpRequestBuilder<*>

    /**
     * 通过key value 添加params
     */
    fun param(key: String, `val`: String): OkHttpRequestBuilder<*>
}
