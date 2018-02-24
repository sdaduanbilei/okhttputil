package com.scorpio.drive

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.scorpio.baselib.http.OkHttpUtils
import com.scorpio.drive.domain.JsonCallback
import okhttp3.Call
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        val file = File("/storage/emulated/0/9ji/1519456887211.jpg")
        OkHttpUtils().post()
                .url("https://m.9ji.com/app/3_0/UserHandler.ashx")
                .param("act", "UploadUserFace")
                .param("userid","54119")
                .params(map)
                .addFile("file","usericon,",file)
                .tag(this)
                .build()
                .execute(object : JsonCallback<String>() {
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.d(TAG,e.toString())
                    }

                    override fun onSucc(response: Any, id: Int) {
                        Log.d(TAG,response.toString())
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        OkHttpUtils().cancelTag(this)
    }
}

