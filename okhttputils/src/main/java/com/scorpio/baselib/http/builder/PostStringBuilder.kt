package com.scorpio.baselib.http.builder

import com.scorpio.baselib.http.request.PostStringRequest
import com.scorpio.baselib.http.request.RequestCall
import okhttp3.MediaType

/**
 * Created by scorpio on 2018/3/7.
 */
class PostStringBuilder() : OkHttpRequestBuilder<PostStringBuilder>() {

    private var content:String? = null
    private var mediaType:MediaType? = null
    fun content(content: String) : PostStringBuilder {
        this.content = content
        return  this
    }

    fun mediaType(mediaType: MediaType): PostStringBuilder {
        this.mediaType = mediaType
        return  this
    }
    override fun build(): RequestCall {
        return PostStringRequest(url, tag, params, headers, content, mediaType, id).build()
    }
}