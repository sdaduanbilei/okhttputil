package com.scorpio.baselib.http.callback

import com.alibaba.fastjson.JSON
import okhttp3.Response

/**
 * Created by sdaduanbilei on 18-1-16.
 */
abstract class StringCallBack : Callback<String>() {
    override fun parseNetworkResponse(response: Response, id: Int):String {
        val json = JSON.parseObject(response.body()!!.string())
        return if (json == null){
            "Response is not json data!!!"
        }else{
            val data = json.getString("data")
            val code = json.getIntValue("code")
            val msg = json.getString("userMsg")

            if (code == 0){
                validateData = data
                json.toString()
            }else{
                msg
            }
        }
    }
}
