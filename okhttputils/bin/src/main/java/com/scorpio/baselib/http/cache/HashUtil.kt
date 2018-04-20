package com.scorpio.baselib.http.cache

import java.security.MessageDigest



/**
 * Created by scorpio on 2018/3/7.
 */
class HashUtil {
    fun hashKeyForDisk(key:String):String{
        val digest = MessageDigest.getInstance("MD5")
        digest.update(key.toByteArray())
        return  bytesToHexString(digest.digest())
    }

    private fun bytesToHexString(bytes: ByteArray?): String {
        val sb = StringBuffer()
        for (i in 0 until bytes!!.size) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }
}