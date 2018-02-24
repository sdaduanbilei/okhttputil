package com.scorpio.baselib.http.builder

import com.scorpio.baselib.http.request.PostFormRequest
import com.scorpio.baselib.http.request.RequestCall
import com.scorpio.baselib.http.utils.FileInput
import java.io.File

/**
 * Created by scorpio on 2018/2/24.
 * 表单提交 包括文件
 *
 */
 class PostFormBuilder : OkHttpRequestBuilder<PostFormBuilder>(),HasParamsable {

    private var files:ArrayList<FileInput> = ArrayList<FileInput>()
    override fun build(): RequestCall {
        return PostFormRequest(url, tag, params, headers,files, id).build()
    }

    fun files(key: String, files: Map<String, File>): PostFormBuilder {
        for (filename in files.keys) {
            this.files.add(FileInput(key, filename, files[filename]))
        }
        return this
    }

    fun addFile(name:String,fileName:String,file:File):PostFormBuilder{
        files.add(FileInput(name,fileName,file))
        return this
    }

    override fun params(params: MutableMap<String, String>): PostFormBuilder {
        this.params = params
        return this
    }

    override fun param(key: String, `val`: String): PostFormBuilder {
        if (this.params.isEmpty()) {
            this.params = HashMap()
        }
        this.params[key] = `val`
        return this
    }
}