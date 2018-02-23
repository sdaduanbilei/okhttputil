package com.scorpio.baselib.http

import com.scorpio.baselib.http.builder.GetBuilder
import com.scorpio.baselib.http.callback.Callback
import com.scorpio.baselib.http.request.RequestCall
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException


/**
 * Created by sdaduanbilei on 18-1-3.
 */
class OkHttpUtils {

    /**
     * dobject 可以定义在全局也可以在类的内部使用
     * object 就是单例模式的化身
     * object 可以实现 Java 中的匿名类
     * companion object 就是 Java 中的 static 变量
     * companion object 只能定义在对应的类中
     */
    companion object {
        const val TAG = "OkHttpUtils"
        const val DEFAULT_MILLISECONDS = 10000L
        private var mOkHttpClint: OkHttpClient? = null
        private var mPlatform: Platform? = null
    }

    /**
     * 初始化
     */
    init {
        if (mOkHttpClint == null) {
            mOkHttpClint = OkHttpClient()
        }
        mPlatform = Platform.get()
    }


    fun getOkHttpClient(): OkHttpClient {
        return mOkHttpClint!!
    }

    fun get(): GetBuilder {
        return GetBuilder()
    }


    /**
     * 执行异步请求
     */
    fun execute(requestCall: RequestCall, callback: Callback<*>) {
        val id = requestCall.getOkHttpRequest()!!.getId()
        requestCall.getCall()!!.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException?) {
                sendFailCallBack(call,IOException(e),callback,id)
            }

            // 响应成功
            override fun onResponse(call: Call, response: Response?) {
                if (call.isCanceled) { // 请求被取消
                    sendFailCallBack(call, IOException("Canceled!!!!"), callback, id)
                    return
                }
                if (!response!!.isSuccessful) { // 请求失败 http code >= 200 && code < 300;
                    sendFailCallBack(call, IOException("request failed ,response code is :" + response.code()), callback, id)
                    return
                }
                // 校验数据合法性
                val validateErrorMsg = callback.validateReponse(response, id)
                if (validateErrorMsg != null) {
                    if (!validateErrorMsg.isEmpty()) {
                        sendFailCallBack(call, IOException(validateErrorMsg), callback, id)
                        return
                    }
                }
                // 校验Object 并返回
                val o = callback.parseNetworkResponse(response, id)
                if (o != null) {
                    sendSuccCallback(o, callback, id)
                } else {
                    sendFailCallBack(call, IOException("object is null !!!"), callback, id)
                }
            }

        })
    }

    private fun sendSuccCallback(o: Any, callback: Callback<*>, id: Int) {
        mPlatform!!.execute(Runnable {
            callback.onSucc(o, id)
            callback.onAfter(id)
        })

    }

    private fun sendFailCallBack(call: Call, ioException: IOException, callback: Callback<*>, id: Int) {
        mPlatform!!.execute(Runnable {
            callback.onError(call, ioException, id)
            callback.onAfter(id)
        })
    }

    private fun cancelTag(tag:Any){
        // 取消队列中的请求
        mOkHttpClint!!.dispatcher().queuedCalls()
                .filter { tag == it.request().tag() }
                .forEach { it.cancel() }
        // 取消正在运行的请求
        mOkHttpClint!!.dispatcher().runningCalls()
                .filter { tag == it.request().tag() }
                .forEach { it.cancel() }
    }

    object METHOD {
        val HEAD = "HEAD"
        val DELETE = "DELETE"
        val PUT = "PUT"
        val PATCH = "PATCH"
    }
}

