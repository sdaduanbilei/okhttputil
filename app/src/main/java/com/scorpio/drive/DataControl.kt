package com.scorpio.drive

import android.os.Build
import com.alibaba.fastjson.JSONObject
import com.scorpio.baselib.http.OkHttpUtils
import com.scorpio.drive.domain.DetailData
import com.scorpio.drive.domain.JsonCallback
import java.io.File

/**
 * Created by scorpio on 2018/3/7.
 */

class DataControl {


    fun test(file:LinkedHashMap<String, File>, callback: JsonCallback<String>) {
//        params.put("Token", userData.getToken())
//        params.put("verison", Utils.currentVersion(context) + "")
//        params.put("Area", userData.getArea())
//        params.put("type", type)
        OkHttpUtils().post().url("https://moa.ch999.com/app/uploadfiles")
                .param("Token","4B2FF13609AE4EB6BC8FF480DAC3B8DC")
                .param("Area","HQ")
                .param("appidentifier",Build.SERIAL)
                .files("file",file)

                .build().execute(callback)

    }

}
