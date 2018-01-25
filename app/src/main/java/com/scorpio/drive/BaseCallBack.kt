package com.scorpio.drive

import com.scorpio.baselib.http.callback.Callback
import okhttp3.Call
import okhttp3.Response

/**
 * Created by sdaduanbilei on 18-1-16.
 */
abstract class BaseCallBack<T>: Callback<T>{
    constructor()
    override fun parseNetworkResponse(response: Response, id: Int): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
