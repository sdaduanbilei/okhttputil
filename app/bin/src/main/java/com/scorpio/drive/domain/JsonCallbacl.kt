package com.scorpio.drive.domain

import com.alibaba.fastjson.JSON
import com.scorpio.baselib.http.callback.GenericsCallback
import com.scorpio.baselib.http.callback.JsonGenericsSerializator


/**
 * Created by scorpio on 2018/2/23.
 */


abstract class JsonCallback<T> : GenericsCallback<T>(JsonGenericsSerializator()) {

    @Throws(Exception::class)
    override fun validateReponse(responseString: String, id: Int): String {
        val json = JSON.parseObject(responseString)
        if (json == null) {
            return "Response is not json data!!!"
        } else {
            val data = json.getString("data")
            if (json.containsKey("stats")) {
                val status = json.getIntValue("stats")
                val msg = json.getString("msg")
                if (status == 1) {
                    validateData = data
                } else {
                    return msg
                }
            } else {
                val code = json.getIntValue("code")
                val msg = json.getString("userMsg")
                if (code == 0) {
                    if (data.isNullOrEmpty()) {
                        validateData = json.toString()
                    } else {
                        validateData = data
                    }
                } else {
                    return msg
                }
            }
        }
        return ""
    }
}