package com.scorpio.drive

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.scorpio.baselib.http.OkHttpUtils
import com.scorpio.baselib.http.utils.Cookie
import com.scorpio.drive.domain.DetailData
import com.scorpio.drive.domain.JsonCallback
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
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
            }

            override fun onCache(response: Any?, id: Int) {
                val detailData = response as DetailData
            }


        })

        OkHttpUtils().get().url("https://m.9ji.com/web/api/products/productCityDetail/v1")
                .tag(this).build().execute(object : JsonCallback<String>() {
            override fun onError(call: Call, e: Exception, id: Int) {
                Log.d("onError",e.toString())
            }

            override fun onSucc(response: Any, id: Int) {
                Log.d("onSucc",response.toString())
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        OkHttpUtils().cancelTag(this)
    }
}

