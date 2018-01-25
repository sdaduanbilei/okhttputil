package com.scorpio.drive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.scorpio.baselib.http.BaseHttp
import com.scorpio.baselib.http.callback.Callback
import com.scorpio.baselib.http.callback.Callback2
import com.scorpio.drive.domain.CityData
import okhttp3.Call
import okhttp3.OkHttpClient
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        https//m.9ji.com/app/2_0/UserCenter.aspx?PlatForm=1&act=startad&version=3.2.2
//        https://m.9ji.com/app/2_0/ProductSearch.aspx?act=newGetTopArea



        val map = HashMap<String,String>()
        map.put("act","newGetTopArea")
        BaseHttp.get()
                .url("https://m.9ji.com/app/2_0/ProductSearch.aspx")
                .param("t",Date().time.toString())
                .params(map)
                .tag("1")
                .build()
                .execute(object : BaseCallBack<List<CityData>>(){
                    override fun onError(call: Call, e: Exception, id: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(response: List<CityData>, id: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
    }
}

