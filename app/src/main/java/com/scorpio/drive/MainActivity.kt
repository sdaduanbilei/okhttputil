package com.scorpio.drive

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.scorpio.baselib.http.BaseHttp
import com.scorpio.drive.domain.HomeData
import com.scorpio.drive.domain.JsonCallback
import okhttp3.Call
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        loadData()
    }

    private fun loadData() {
        val map = HashMap<String, String>()
        map["label"] = "891839124172845058"
        map["cityId"] = "5301029"
        BaseHttp().get()
                .url("https://m.9ji.com/web/api/floors/v2")
                .param("t", Date().time.toString())
                .params(map)
                .tag("1")
                .build()
                .execute(object : JsonCallback<HomeData>() {
                    override fun onResponse(response: Any, id: Int) {
                        val homeData = response as HomeData
                        Log.d(TAG,homeData.label[0].title)
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.d(TAG,e!!.localizedMessage)
                    }
                })
    }
}

