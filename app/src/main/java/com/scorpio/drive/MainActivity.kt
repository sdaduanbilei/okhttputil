package com.scorpio.drive

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.scorpio.baselib.http.OkHttpUtils
import com.scorpio.baselib.http.utils.Cookie
import com.scorpio.drive.domain.DetailData
import com.scorpio.drive.domain.JsonCallback
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Cookie().setCookie(applicationContext,".9ji.com","cityId=" + Date().time)
        loadData()
    }





    private fun loadData() {

        DataControl().test(this,object : JsonCallback<DetailData>(){
            override fun onError(call: okhttp3.Call, e: Exception, id: Int) {
                Log.d("onError" ,e.toString())
            }

            override fun onSucc(response: Any, id: Int) {
                val detailData = response as DetailData
                mText.text = "onSucc ===" + detailData.instalment.description
            }

            override fun onCache(response: Any?, id: Int) {
                val detailData = response as DetailData
                mText.text = "cache ===" + detailData.profile.title
            }


        })

//        OkHttpUtils().get()
//                .url("https://www.9ji.com/topic/Android/9ji.apk")
//                .build()
//                .execute(object : FileCallBack(Environment.getExternalStorageDirectory().absolutePath, Date().time.toString() + "_9ji.apk"){
//                    override fun onError(call: Call, e: Exception, id: Int) {
//                        Log.d("onError",e.toString())
//                    }
//
//                    override fun inProgress(progress: Float, total: Long, id: Int) {
//                        super.inProgress(progress, total, id)
//                        Log.d("inProgress", progress.toString() + "==" + total)
//                    }
//
//                    override fun onSucc(response: Any, id: Int) {
//                        val file = response as File
//                        Log.d("file",file.absolutePath)
//                    }
//                })
    }

    override fun onDestroy() {
        super.onDestroy()
        OkHttpUtils().cancelTag(this)
    }
}

