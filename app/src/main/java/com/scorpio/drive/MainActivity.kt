package com.scorpio.drive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.scorpio.baselib.http.BaseHttp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val map = HashMap<String,String>()
        map.put("cityid","530102")
        map.put("userid","54119")
        BaseHttp.get()
                .url("http://m.9ji.com/web/api/info?keys=666")
                .param("1","12")
                .param("key2","val2")
                .params(map)
                .tag("1")
                .build()
    }
}

