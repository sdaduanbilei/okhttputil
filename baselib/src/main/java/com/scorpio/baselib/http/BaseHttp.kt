package com.scorpio.baselib.http

import android.util.Log
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
class BaseHttp {
val TAG = "BaseHttp"
    val DEFAULT_MILLISECONDS = 10000L
    private var mOkHttpCleint:OkHttpClient? = null
    private var mPlatform: Platform? = null


    init {
        if (this.mOkHttpCleint == null){
            this.mOkHttpCleint = OkHttpClient()
        }
        mPlatform = Platform.get()
    }


    fun getOkHttpClient(): OkHttpClient {
        return mOkHttpCleint!!
    }

    fun get(): GetBuilder {
        return GetBuilder()
    }

    fun execute(requestCall: RequestCall, callback: Callback<*>) {

        val finalCallback = callback
        val id = requestCall.getOkHttpRequest()!!.getId()
        requestCall.getCall()!!.enqueue(object :okhttp3.Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d(TAG,e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                if (call!!.isCanceled){
                    sendFailCallBack(call,IOException("Canceled!!!!"),finalCallback,id)
                    return
                }

                if (!response!!.isSuccessful){
                    sendFailCallBack(call, IOException("request failed ,response code is :" + response.code()),finalCallback,id)
                    return
                }
                // 校验数据合法性
                val validateErrorMsg = finalCallback.validateReponse(response,id)
                if (validateErrorMsg != null) {
                    if (!validateErrorMsg.isEmpty()){
                        sendFailCallBack(call, IOException(validateErrorMsg),finalCallback, id)
                        return
                    }
                }

                // 校验respone
                val o = finalCallback.parseNetworkResponse(response,id)
                if (o!=null) {
                    sendSuccCallback(o,finalCallback,id)
                }else{
                    sendFailCallBack(call,IOException("object is null !!!"),finalCallback,id)
                }
            }

        })
    }

    private fun sendSuccCallback(o: Any, callback: Callback<*>, id: Int) {
        mPlatform!!.execute(Runnable {
            callback.onResponse(o,id)
            callback.onAfter(id)
        })

    }

    private fun sendFailCallBack(call: Call, ioException: IOException, callback: Callback<*>,id: Int) {
        mPlatform!!.execute(Runnable {
            callback.onError(call,ioException,id)
            callback.onAfter(id)
        })

    }
}

