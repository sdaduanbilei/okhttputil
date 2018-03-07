package com.scorpio.drive

import android.app.Application
import com.scorpio.baselib.http.OkHttpUtils
import com.scorpio.baselib.http.cookie.CookieJarImpl
import com.scorpio.baselib.http.cookie.store.PersistentCookieStore
import okhttp3.OkHttpClient

/**
 * Created by sdaduanbilei on 18-1-11.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val cookieJar = CookieJarImpl(PersistentCookieStore(applicationContext))
        val client = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build()
        OkHttpUtils().initClient(client,applicationContext)
    }
}