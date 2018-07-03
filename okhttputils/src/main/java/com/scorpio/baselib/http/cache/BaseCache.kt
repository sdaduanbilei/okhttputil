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
    private val maxDiskSize:Long = 50 * (1024 * 1024)

    init {
        val version = context.packageManager.getPackageInfo(context.packageName,0).versionCode
        mDiskLruCache = DiskLruCache.open(File(filePath,fileName),version,1,maxDiskSize)
    }

    fun addCache(data:String?,response: Response){
        val buffer = Buffer()
        if (data.isNullOrEmpty()){
            return
        }
        try {
            buffer.write(data!!.toByteArray())
            val rawResponse = buffer.readByteArray()
            val key = response.request().url().toString()
            val editor = mDiskLruCache!!.edit(HashUtil().hashKeyForDisk(key))
            if (editor!=null){
                editor.set(0, String(rawResponse, Charset.defaultCharset()))
                editor.commit()
                buffer.clone()
            }
        } catch (exc: IOException) {
            buffer.clone()
            exc.printStackTrace()
        }
    }

    fun getCache(request: Request): ResponseBody? {
        val key = request.url().toString()
        val cacheKey = HashUtil().hashKeyForDisk(key)
        val cacheSnapshot = mDiskLruCache!!.get(cacheKey)
        return if (cacheSnapshot != null) {
            ResponseBody.create(null,cacheSnapshot.getString(0).toByteArray())
        }else{
            null
        }

    }

}