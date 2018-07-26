package com.scorpio.baselib.http

import android.content.Context
import android.util.Log
import com.scorpio.baselib.http.builder.GetBuilder
import com.scorpio.baselib.http.builder.PostFormBuilder
import com.scorpio.baselib.http.builder.PostStringBuilder
import com.scorpio.baselib.http.cache.BaseCache
import com.scorpio.baselib.http.callback.Callback
import com.scorpio.baselib.http.callback.FileCallBack
import com.scorpio.baselib.http.request.RequestCall
import com.scorpio.baselib.http.utils.Platform
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.util.concurrent.Executor


/**
 * Created by sdaduanbilei on 18-1-3.
 */
class OkHttpUtils {

    /**
     * object 可以定义在全局也可以在类的内部使用
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
        private var mBaseCache: BaseCache? = null

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


    fun initClient(client: OkHttpClient?, context: Context) {
        mBaseCache = BaseCache(context)
        mOkHttpClint = client
    }

    fun getDelivery(): Executor {
        return mPlatform!!.defaultCallbackExecutor()
    }

    fun getOkHttpClient(): OkHttpClient {
        return mOkHttpClint!!
    }

    fun get(): GetBuilder {
        return GetBuilder()
    }

    fun post(): PostFormBuilder {
        return PostFormBuilder()
    }

    fun postString(): PostStringBuilder {
        return PostStringBuilder()
    }


    /**
     * 执行异步请求
     */
    fun execute(requestCall: RequestCall, callback: Callback<*>) {
        val id = requestCall.getOkHttpRequest()!!.getId()
        mPlatform!!.execute(Runnable {
            val responseBody: ResponseBody? = mBaseCache!!.getCache(requestCall.getRequest()!!)
            if (responseBody != null) {
                // 校验Object 并返回
                callback.validateReponse(responseBody.string(), id)!!
                val o = callback.parseNetworkResponse(responseBody.string(), null, id)
                val extra = callback.extraData
                sendSuccCallback(ResponseResult(true, o,extra), callback, id)
            }
        })

        requestCall.getCall()!!.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException?) {
                sendFailCallBack(call, IOException(e), callback, id)
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

                // 校验数据合法性 // filecallback 不进行数据校验
                var result =  ""
                if (callback !is FileCallBack){
                    result = response.body()!!.string()
                    val validateErrorMsg: String = callback.validateReponse(result, id)!!
                    if (!validateErrorMsg.isEmpty()) {
                        sendFailCallBack(call, IOException(validateErrorMsg), callback, id)
                        return
                    }
                }
                // 校验Object 并返回
                val o = callback.parseNetworkResponse(result, response, id)
                val extra = callback.extraData
                if (o != null) {
                    mBaseCache!!.addCache(result, response)
                    sendSuccCallback(ResponseResult(false, o,extra), callback, id)
                } else {
                    sendFailCallBack(call, IOException("object is null !!!"), callback, id)
                }
            }

        })
    }

    private fun sendSuccCallback(o: Any, callback: Callback<*>, id: Int) {
        mPlatform!!.execute(Runnable {
            val responseResult: ResponseResult<Any> = o as ResponseResult<Any>
            if (responseResult.isCache) {
                callback.onCache(responseResult.result, id)
                callback.onCache(responseResult.result,responseResult.extra,id)
            } else {
                callback.onSucc(responseResult.result!!, responseResult.extra,id)
            }
            callback.onAfter(id)
        })


    }


    private fun sendFailCallBack(call: Call, ioException: IOException, callback: Callback<*>, id: Int) {
        mPlatform!!.execute(Runnable {
            callback.onError(call, ioException, id)
            callback.onAfter(id)
        })
    }

    fun cancelTag(tag: Any) {
        // 取消队列中的请求
        for (call in mOkHttpClint!!.dispatcher().queuedCalls()) {
            if (tag == call.request().tag()) {
                call.cancel()
            }
        }
        for (call in mOkHttpClint!!.dispatcher().runningCalls()) {
            if (tag == call.request().tag()) {
                call.cancel()
            }
        }
    }
}

