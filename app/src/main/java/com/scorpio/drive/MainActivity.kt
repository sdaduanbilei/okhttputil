package com.scorpio.drive

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import com.scorpio.baselib.http.BaseHttp
import com.scorpio.drive.domain.CityData
import okhttp3.Call
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        loadData()
    }

    private fun loadData() {
        val map = HashMap<String, String>()
        map["act"] = "GetDisposeArea"
        BaseHttp().get()
                .url("https://m.9ji.com/app/2_0/ProductSearch.aspx")
                .param("t", Date().time.toString())
                .params(map)
                .tag("1")
                .build()
                .execute(object : BaseCallBack<List<CityData>>() {
                    override fun onError(call: Call, e: Exception, id: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(response: List<CityData>, id: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
    }
}

