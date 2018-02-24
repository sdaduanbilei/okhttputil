package com.scorpio.drive.domain

import com.alibaba.fastjson.JSON
import com.scorpio.baselib.http.callback.GenericsCallback
import com.scorpio.baselib.http.callback.JsonGenericsSerializator
import okhttp3.Response


/**
 * Created by scorpio on 2018/2/23.
 */


abstract class JsonCallback<T> : GenericsCallback<T>(JsonGenericsSerializator()) {

    @Throws(Exception::class)
    override fun validateReponse(response: Response, id: Int): String {
        val json = JSON.parseObject(response.body()!!.string())
        if (json == null){
            return "Response is not json data!!!"
        }else{
            val data = json.getString("data")
            val code = json.getIntValue("code")
            val msg = json.getString("userMsg")
            if (code == 0){
                validateData = data
            }else{
                return msg
            }
        }
        return  ""
    }
}