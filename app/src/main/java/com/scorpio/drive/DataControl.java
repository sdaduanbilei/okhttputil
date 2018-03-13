package com.scorpio.drive;

import com.alibaba.fastjson.JSONObject;
import com.scorpio.baselib.http.OkHttpUtils;
import com.scorpio.drive.domain.DetailData;
import com.scorpio.drive.domain.JsonCallback;

/**
 * Created by scorpio on 2018/3/7.
 */

public class DataControl {




	public void test(Object tag,JsonCallback<DetailData> callback) {
		String jsonStr = "{\"isCache\":\"null\"}";
		JSONObject json = JSONObject.parseObject(jsonStr);
		new OkHttpUtils()
				.get()
				.url("https://m.9ji.com/web/api/products/productCityDetail/v1")
				.param("ppid",62033)
				.param("result",null)
				.param("isCache",json.getBooleanValue("isCache"))
				.addHeader("key","city")
				.tag(tag)
				.build()
				.execute(callback);
	}

}
