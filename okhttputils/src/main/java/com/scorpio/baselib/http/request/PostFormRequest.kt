package com.scorpio.baselib.http.request

import com.scorpio.baselib.http.utils.FileInput
import okhttp3.*
import java.net.URLConnection
import java.net.URLEncoder

/**
 * Created by scorpio on 2018/2/24.
 */
class PostFormRequest(url: String, tag: Any, params: Map<String, String>, headers: Map<String, String>, files: ArrayList<FileInput> ,id: Int) : OkHttpRequest(url, tag, params, headers, id) {

    private var files : ArrayList<FileInput>? = null

    init {
        this.files = files
    }

    override fun buildRequestBody(): RequestBody? {
        // 无文件
        if (files!!.isEmpty()){
            val builder:FormBody.Builder = FormBody.Builder()
            addParams(builder)
            return  builder.build()
        }else{
            val builder:MultipartBody.Builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
            addParams(builder)
            for (i in files!!.indices) {
                val fileInput = files!![i]
                val fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.file!!.path)!!), fileInput.file!!)
                builder.addFormDataPart(fileInput.key, fileInput.filename, fileBody)
            }
            return builder.build()
        }
    }

    private fun guessMimeType(path: String): String? {
        val fileNameMap = URLConnection.getFileNameMap()
        var contentTypeFor:String = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"))
        if (contentTypeFor.isEmpty()){
            contentTypeFor = "application/octet-stream"
        }
        return contentTypeFor
    }

    /**
     * 添加formdata 参数
     */
    private fun addParams(builder:MultipartBody.Builder){
        if (!params.isEmpty()){
            for (key in params.keys){
                builder.addPart(Headers.of("Content-Disposition","form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params[key]!!))
            }
        }
    }

    /**
     * 添加formdata 参数
     */
    private fun addParams(builder: FormBody.Builder) {
        if (!params.isEmpty()){
            for (key in params.keys){
                builder.add(key, params[key]!!)
            }
        }
    }

    override fun buildRequest(requestBody: RequestBody?): Request {
        return builder.post(requestBody!!).build()
    }
}