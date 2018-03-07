package com.scorpio.drive;

import com.alibaba.fastjson.JSONObject;
import com.scorpio.baselib.http.OkHttpUtils;
import com.scorpio.drive.domain.JsonCallback;

/**
 * Created by scorpio on 2018/3/7.
 */

public class DataControl {

	public void test(Object tag,JsonCallback<String> callback) {

		JSONObject json = new JSONObject();
		json.put("imei","352677080235525");
		new OkHttpUtils()
				.postString()
				.url("https://m.9ji.com/web/api/phoneDevices/add/v1")
				.content(json.toString())
				.addHeader("City","530102")
				.tag(tag)
				.build()
				.execute(callback);
	}

}
