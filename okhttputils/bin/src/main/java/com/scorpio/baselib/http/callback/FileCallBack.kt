package com.scorpio.baselib.http.callback


import android.os.SystemClock
import com.scorpio.baselib.http.OkHttpUtils
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by zhy on 15/12/15.
 */
abstract class FileCallBack(
        /**
         * 目标文件存储的文件夹路径
         */
        private val destFileDir: String,
        /**
         * 目标文件存储的文件名
         */
        private val destFileName: String) : Callback<File>() {


    private var lastUpdateTime: Long = 0
    private val DEFAULT_RATE = 100


    @Throws(Exception::class)
    override fun parseNetworkResponse(responseString: String?, response: Response?, id: Int): File? {
        return saveFile(response, id)
    }


    @Throws(IOException::class)
    private fun saveFile(response: Response?, id: Int): File {

            val buf = ByteArray(1024)
            var len = 0
            val inputStream = response!!.body()!!.byteStream()
            val total = response!!.body()!!.contentLength()
            var sum: Float = 0f
            val dir = File(destFileDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, destFileName)
            val fos = FileOutputStream(file)
            do {
                len = inputStream.read(buf)
                if (len != -1) {
                    sum += len.toLong()
                    fos.write(buf, 0, len)
                    OkHttpUtils().getDelivery().execute(Runnable {
                        if (updateProgress()) {
                            inProgress( sum * 1.0f / total, total, id)
                        }
                    })
                }
            } while (len != -1)
            fos.flush()
            return file
    }

    private fun updateProgress(): Boolean {
        val currTime = SystemClock.uptimeMillis()
        if (currTime - this.lastUpdateTime >= DEFAULT_RATE) {
            this.lastUpdateTime = currTime
            return true
        }
        return false
    }


}
