package com.scorpio.drive

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        DataControl().test(this,object :JsonCallback<String>(){
            override fun onError(call: Call, e: Exception, id: Int) {

            }

            override fun onSucc(response: Any, id: Int) {
                mText.text = "onSucc ===" + response.toString()
            }

            override fun onCache(response: Any?, id: Int) {
                mText.text = "cache ===" + response.toString()
            }


        })


    }

    override fun onDestroy() {
        super.onDestroy()
        OkHttpUtils().cancelTag(this)
    }
}

