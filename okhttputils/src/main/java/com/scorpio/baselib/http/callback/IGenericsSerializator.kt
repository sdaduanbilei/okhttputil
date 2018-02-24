package com.scorpio.baselib.http.callback

import java.lang.reflect.Type

/**
 * Created by scorpio on 2018/2/23.
 */
interface IGenericsSerializator {
    fun <T> transform(response: String, type: Type): T
}