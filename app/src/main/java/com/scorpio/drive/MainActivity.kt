package com.scorpio.drive

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.scorpio.baselib.http.OkHttpUtils
import com.scorpio.drive.domain.JsonCallback
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
    }

    private fun loadData() {
        OkHttpUtils().get()
                .url("https://m.9ji.com/web/api/products/productCityDetail/v1")
                .param("ppid", "58206")
                .tag(this)
                .build()
                .execute(object : JsonCallback<String>() {
                    override fun onCache(response: Any?, id: Int) {
                        mText.text = "cache======" + response.toString()
                    }

                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.d(TAG, "onError" + e.localizedMessage)
                    }

                    override fun onSucc(response: Any, id: Int) {
                        mText.text = "onSucc"
                    }

                })

    }

    override fun onDestroy() {
        super.onDestroy()
        OkHttpUtils().cancelTag(this)
    }
}

