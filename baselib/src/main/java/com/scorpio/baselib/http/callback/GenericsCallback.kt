package com.scorpio.baselib.http.callback

import android.util.Log
import okhttp3.Call
import okhttp3.Response
import java.lang.reflect.ParameterizedType


/**
 * Created by sdaduanbilei on 18-1-16.
 */
abstract class GenericsCallback<T>(jsonGenericsSerializator: JsonGenericsSerializator) : Callback<T>() {
    private var mGenericsSerializator :IGenericsSerializator? =null
    init {
        mGenericsSerializator = jsonGenericsSerializator
    }

    override fun onResponse(call: Call?, response: Response?) {
        Log.d(TAG,response!!.body()!!.string())
    }

    override fun onError(call: Call, e: Exception, id: Int) {
        Log.d(TAG,e.localizedMessage)
    }


    override fun parseNetworkResponse(response: Response, id: Int): T? {
        if (validateData!!.isEmpty()){
            return null
        }

        val superclass = javaClass.genericSuperclass
        if (superclass is Class<*>) {
            //表示未设置泛型T
            return null
        }

        val parameterize = superclass as ParameterizedType
        val mType = parameterize.actualTypeArguments[0]
        return if (mType === String::class.java) {
            validateData as T
        } else {
            mGenericsSerializator!!.transform(validateData!!, mType)
        }
    }
}
