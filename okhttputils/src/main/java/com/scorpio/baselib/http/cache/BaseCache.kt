package com.scorpio.baselib.http.cache

import android.content.Context
import com.jakewharton.disklrucache.DiskLruCache
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


/**
 * Created by scorpio on 2018/3/7.
 */
class BaseCache(context: Context) {
    private var mDiskLruCache:DiskLruCache? =null
    private var fileName = "cache"
    private var filePath =  context.cacheDir.path
    private val maxDiskSize:Long = 20 * (1024 * 1024)

    init {
        mDiskLruCache = DiskLruCache.open(File(filePath,fileName),1,1,maxDiskSize)
    }

    fun addCache(data:String?,response: Response){
        val buffer = Buffer()
        if (data.isNullOrEmpty()){
            return
        }
        try {
            buffer.write(data!!.toByteArray())
            val rawResponse = buffer.readByteArray()
            val editor = mDiskLruCache!!.edit(HashUtil().hashKeyForDisk(response.request().url().toString()))
            editor.set(0, String(rawResponse, Charset.defaultCharset()))
            editor.commit()
            buffer.clone()
        } catch (exc: IOException) {
            buffer.clone()
        }
    }

    fun getCache(request: Request): ResponseBody? {
        val cacheKey = HashUtil().hashKeyForDisk(request.url().toString())
        val cacheSnapshot = mDiskLruCache!!.get(cacheKey)
        if (cacheSnapshot != null) {
            return ResponseBody.create(null,cacheSnapshot.getString(0).toByteArray())
        }else{
            return null
        }

    }

}