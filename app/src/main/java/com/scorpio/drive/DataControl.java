package com.scorpio.drive;

import com.scorpio.baselib.http.OkHttpUtils;
import com.scorpio.drive.domain.JsonCallback;

/**
 * Created by scorpio on 2018/3/7.
 */

public class DataControl {

	public void test(Object tag,JsonCallback<String> callback) {
		new OkHttpUtils().get()
				.url("http://119.27.170.36:805/api/services/app/mobileApp/GetIndex")
				.tag(tag)
				.build()
				.execute(callback);
	}

}
