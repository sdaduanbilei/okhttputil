package com.scorpio.drive

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.scorpio.baselib.http.OkHttpUtils
import com.scorpio.baselib.http.utils.Cookie
import com.scorpio.drive.domain.JsonCallback
import kotlinx.android.synthetic.main.activity_main.*
import me.nereo.multi_image_selector.MultiImageSelectorActivity
import okhttp3.Call
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.util.*
import kotlin.collections.LinkedHashMap


class MainActivity : AppCompatActivity() {

    private var imgSelect1 = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Cookie().setCookie(applicationContext,".9ji.com","cityId=" + Date().time)

//        MultiImageSelector.create(this)
//                .count(3)
//                .start(this, 2002)

        loadData()



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2002) {
            imgSelect1 = data!!.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT)
            compree(imgSelect1)
        }
    }


    fun compree(imgSelect1: ArrayList<String>) {
        val saveFile = Environment.getExternalStorageDirectory().absoluteFile.toString() + "/file/"
        val file = File(saveFile)
        if (!file.exists()) {
            file.mkdirs()
        }

        val files1 = LinkedHashMap<String, File>()
        Luban.with(baseContext).load(imgSelect1)
                .ignoreBy(100).setTargetDir(saveFile).setCompressListener(object : OnCompressListener {
                    override fun onSuccess(file: File?) {
                        files1[file!!.name] = file

                        if (files1.size == imgSelect1.size){
                            DataControl().test(files1,object :JsonCallback<String>(){
                                override fun onError(call: Call, e: Exception, id: Int) {

                                }

                                override fun onSucc(response: Any, extraData:String,id: Int) {

                                }

                            })
                        }
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onStart() {
                    }

                }).launch()
    }



    private fun loadData() {
        OkHttpUtils().get().url("https://m.9ji.com/web/api/sc/products/getDetailStatic/v1")
                .addHeader("token","12312")
                .param("ppid","62700")
                .tag(this).build().execute(object : JsonCallback<String>() {
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.d("onError",e.toString())
                    }

                    override fun onCache(response: Any?, id: Int) {
                        super.onCache(response, id)
                    }

                    override fun onCache(response: Any?, extra: String, id: Int) {
                        super.onCache(response, extra, id)
                        Log.d(TAG, "extaraCache: " +extra)
                    }

                    override fun onSucc(response: Any, extraData: String, id: Int) {
                        Log.d(TAG, "extraData: $extraData")
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        OkHttpUtils().cancelTag(this)
    }
}

