package com.scorpio.drive


import com.scorpio.baselib.http.builder.GetBuilder
import com.scorpio.baselib.http.callback.Callback
import com.scorpio.baselib.http.request.RequestCall
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.Executor

/**
 * Created by zhy on 15/8/17.
 */
class OkHttpUtils(okHttpClient: OkHttpClient?) {
    var okHttpClient: OkHttpClient? = null
        private set
    private val mPlatform: Platform


    val delivery: Executor
        get() = mPlatform.defaultCallbackExecutor()

    init {
        if (okHttpClient == null) {
            this.okHttpClient = OkHttpClient()
        } else {
            this.okHttpClient = okHttpClient
        }

        mPlatform = Platform.get()
    }

    fun execute(requestCall: RequestCall, callback: Callback<*>) {
        var callback = callback
        val finalCallback = callback
        val id = requestCall.getOkHttpRequest()!!.getId()

        requestCall.getCall()!!.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                sendFailResultCallback(call, e, finalCallback, id)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    if (call.isCanceled) {
                        sendFailResultCallback(call, IOException("Canceled!"), finalCallback, id)
                        return
                    }

                    if (!response.isSuccessful) {
                        sendFailResultCallback(call, IOException("request failed , reponse's code is : " + response.code()), finalCallback, id)
                        return
                    }

                    val validateErrorMsg = finalCallback.validateReponse(response, id)
                    if (validateErrorMsg != null) {
                        sendFailResultCallback(call, IOException(validateErrorMsg), finalCallback, id)
                        return
                    }

                    val o = finalCallback.parseNetworkResponse(response, id)
                    if (o != null) {
                        sendSuccessResultCallback(o, finalCallback, id)
                    }
                } catch (e: Exception) {
                    sendFailResultCallback(call, e, finalCallback, id)
                } finally {
                    if (response.body() != null)
                        response.body()!!.close()
                }

            }
        })
    }


    fun sendFailResultCallback(call: Call, e: Exception, callback: Callback<*>, id: Int) {
        mPlatform.execute(Runnable {
            callback.onError(call, e, id)
            callback.onAfter(id)
        })
    }

    fun sendSuccessResultCallback(`object`: Any, callback: Callback<*>, id: Int) {
        mPlatform.execute(Runnable {
//            callback.onResponse(`object`, id)
            callback.onAfter(id)
        })
    }

    fun cancelTag(tag: Any) {
        for (call in okHttpClient!!.dispatcher().queuedCalls()) {
            if (tag == call.request().tag()) {
                call.cancel()
            }
        }
        for (call in okHttpClient!!.dispatcher().runningCalls()) {
            if (tag == call.request().tag()) {
                call.cancel()
            }
        }
    }

    object METHOD {
        val HEAD = "HEAD"
        val DELETE = "DELETE"
        val PUT = "PUT"
        val PATCH = "PATCH"
    }

    companion object {
        val DEFAULT_MILLISECONDS = 10_000L
        @Volatile
        private var mInstance: OkHttpUtils? = null


        fun initClient(okHttpClient: OkHttpClient?): OkHttpUtils? {
            if (mInstance == null) {
                synchronized(OkHttpUtils::class.java) {
                    if (mInstance == null) {
                        mInstance = OkHttpUtils(okHttpClient)
                    }
                }
            }
            return mInstance
        }

        val instance: OkHttpUtils?
            get() = initClient(null)

        fun get(): GetBuilder {
            return GetBuilder()
        }


    }
}

