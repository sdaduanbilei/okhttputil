package com.scorpio.drive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.scorpio.baselib.http.BaseHttp
import com.scorpio.baselib.http.builder.GetBuilder
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val map = HashMap<String, String>()

        BaseHttp.get().param("v","1")



    }
}

