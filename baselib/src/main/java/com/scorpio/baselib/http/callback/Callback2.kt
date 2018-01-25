package com.scorpio.baselib.http.callback

import okhttp3.Call
import okhttp3.Request
import okhttp3.Response


abstract class Callback2<out T> {

    abstract fun onSucc():T
}