package com.scorpio.baselib.http.callback

import okhttp3.Call
import okhttp3.Request
import okhttp3.Response


abstract class Callback<T> {
    val TAG = "Callback"

    /**
     * 用于验证数据合法性后,生成需要的数据对象
     */
    var validateData: String = ""

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
    open fun inProgress(progress: Float, total: Long, id: Int) {

    }

    /**
     * 缓存
     */
    open fun onCache(response: Any?,id: Int){

    }

    /**
     * 自定义验证数据合法性
     * @param response
     * @param id
     * @return 返回null表示没有错误,String为数据出错原因
     */
    @Throws(Exception::class)
    open fun validateReponse(response: String, id: Int): String? {
        return null
    }
    /**
     * Thread Pool Thread
     *
     * @param response
     */
    @Throws(Exception::class)
    abstract fun parseNetworkResponse(responseString: String?, response: Response?, id: Int): T?
    abstract fun onError(call: Call, e: Exception, id: Int)
    abstract fun onSucc(response: Any, id: Int)


    companion object {
        val CALLBACK_DEFAULT = object : Callback<Any>() {
            override fun onCache(response: Any?, id: Int) {

            }

            override fun onSucc(response: Any, id: Int) {
            }

            override fun parseNetworkResponse(responseString: String?, response: Response?,id: Int): Any? {
                return null
            }

            override fun onError(call: Call, e: Exception, id: Int) {
            }
        }
    }
}