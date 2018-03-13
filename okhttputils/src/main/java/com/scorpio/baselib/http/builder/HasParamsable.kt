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
    fun param(key: String, value: String?): OkHttpRequestBuilder<*>

    fun param(key: String, value: Int): OkHttpRequestBuilder<*>

    fun param(key: String, value: Boolean): OkHttpRequestBuilder<*>


}
