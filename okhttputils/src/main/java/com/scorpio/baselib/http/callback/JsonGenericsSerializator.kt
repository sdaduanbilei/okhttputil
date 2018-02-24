package com.scorpio.baselib.http.callback

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Created by scorpio on 2018/2/23.
 */
class JsonGenericsSerializator :IGenericsSerializator{
    override fun <T> transform(response: String, type: Type): T {
        return Gson().fromJson(response,type)
    }
}