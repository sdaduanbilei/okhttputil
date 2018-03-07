package com.scorpio.baselib.http.callback

import java.lang.reflect.ParameterizedType


/**
 * Created by sdaduanbilei on 18-1-16.
 * 泛型回调
 */
abstract class GenericsCallback<T>(jsonGenericsSerializator: JsonGenericsSerializator) : Callback<T>() {
    private var mGenericsSerializator :IGenericsSerializator? = null
    init {
        mGenericsSerializator = jsonGenericsSerializator
    }

    override fun parseNetworkResponse(response: String?, id: Int): T? {
        // 数据验证不通过，json data 为空
        if (validateData.isEmpty()){
            return null
        }
        val superclass = javaClass.genericSuperclass
        if (superclass is Class<*>) {
            //表示未设置泛型T
            return null
        }

        val parameterize = superclass as ParameterizedType
        val mType = parameterize.actualTypeArguments[0]
        return if (mType === String::class.java) { // 支持直接返回 JSON String
            validateData as T
        } else {
            // response  2 object 以后可以转换
            mGenericsSerializator!!.transform(validateData!!, mType)
            // 问题1 Protocol Buffer 直接实现一个 GenericsSerializator 是否可以直接转换
        }
    }
}
