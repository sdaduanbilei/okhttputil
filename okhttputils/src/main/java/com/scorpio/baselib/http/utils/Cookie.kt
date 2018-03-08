package com.scorpio.baselib.http.utils

import android.content.Context
import android.webkit.CookieManager
import android.webkit.CookieSyncManager

/**
 * Created by scorpio on 2018/3/8.
 */
class Cookie {

    fun setCookie(context: Context, url: String, t: String) {
        CookieSyncManager.createInstance(context)
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setCookie(url,t)
        CookieSyncManager.getInstance().sync()

    }

    fun getCookie(context: Context, url: String) :String? {
        CookieSyncManager.createInstance(context)
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        return  cookieManager.getCookie(url)
    }
}