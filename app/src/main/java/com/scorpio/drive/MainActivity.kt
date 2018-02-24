package com.scorpio.drive

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.scorpio.baselib.http.OkHttpUtils
import com.scorpio.drive.domain.HomeData
import com.scorpio.drive.domain.JsonCallback
import okhttp3.Call
import java.util.*


class MainActivity : AppCompatActivity() {

    private var mProgressDialog :ProgressDialog? =null

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mProgressDialog = ProgressDialog.show(this,"","loading")
        loadData()
    }

    private fun loadData() {

        OkHttpUtils().get()
                .url("https://m.9ji.com/web/api/products/productCityDetail/v1")
                .param("ppid","582069")
                .tag(this)
                .build()
                .execute(object:JsonCallback<String>(){
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.d(TAG,"onError" + e.localizedMessage)
                    }

                    override fun onSucc(response: Any, id: Int) {
                        Log.d(TAG,response.toString())
                    }

                })


        val map = HashMap<String, String>()
        map["label"] = ""
        map["cityId"] = "530102"
        OkHttpUtils().get()
                .url("https://m.9ji.com/web/api/floors/v1")
                .param("t", Date().time.toString())
                .params(map)
                .tag(this)
                .build()
                .execute(object : JsonCallback<HomeData>() {
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.d(TAG,e.toString())
                    }

                    override fun onSucc(response: Any, id: Int) {
                        val homeData = response as HomeData
                        Log.d(TAG,homeData.label[0].title)
                    }
                })
    }
}

