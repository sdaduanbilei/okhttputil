package com.scorpio.baselib.http.request

import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody

/**
 * Created by scorpio on 2018/3/7.
 */
class PostStringRequest(url: String, tag: Any, params: MutableMap<String, String>, headers: MutableMap<String, String>, content: String?, mediaType: MediaType?, id: Int) : OkHttpRequest(url, tag, params, headers, id){

    private var mediaType:MediaType = MediaType.parse("application/json;charset=utf-8")!!
    private var content:String? = null
    init {
        this.content = content
        if (content.isNullOrEmpty()){
            throw IllegalArgumentException("the content can not be null !")
        }

        if (mediaType != null){
            this.mediaType = mediaType
        }
    }
    override fun buildRequestBody(): RequestBody? {
        return RequestBody.create(mediaType,content)
    }

    override fun buildRequest(requestBody: RequestBody?): Request {
        return builder.post(requestBody).build()
    }
}