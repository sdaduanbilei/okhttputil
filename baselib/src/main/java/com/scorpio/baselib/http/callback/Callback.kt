package com.scorpio.baselib.http.callback

import okhttp3.Call
import okhttp3.Request
import okhttp3.Response


abstract class Callback<T> {
    /**
     * 用于验证数据合法性后,生成需要的数据对象
     */
    var validateData: String? = null

    /**
     * UI Thread
     *
     * @param request
     */
    fun onBefore(request: Request, id: Int) {}

    /**
     * UI Thread
     *
     * @param
     */
    fun onAfter(id: Int) {}

    /**
     * UI Thread
     *
     * @param progress
     */
    fun inProgress(progress: Float, total: Long, id: Int) {

    }

    /**
     * 自定义验证数据合法性
     * @param response
     * @param id
     * @return 返回null表示没有错误,String为数据出错原因
     */
    @Throws(Exception::class)
    fun validateReponse(response: Response, id: Int): String? {
        return null
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    @Throws(Exception::class)
    abstract fun parseNetworkResponse(response: Response, id: Int): T

    abstract fun onError(call: Call, e: Exception, id: Int)

    abstract fun onResponse(response: T, id: Int)

    companion object {

//        var CALLBACK_DEFAULT : Callback = object :Callback{
//
//        }

//        var CALLBACK_DEFAULT: Callback<*> = object : Callback() {
//
//            @Throws(Exception::class)
//            override fun parseNetworkResponse(response: Response, id: Int): Any? {
//                return null
//            }
//
//            override fun onError(call: Call, e: Exception, id: Int) {
//
//            }
//
//            override fun onResponse(response: Any, id: Int) {
//
//            }
//        }
    }

}