package com.scorpio.drive;

import com.scorpio.baselib.http.OkHttpUtils;
import com.scorpio.drive.domain.DetailData;
import com.scorpio.drive.domain.JsonCallback;

/**
 * Created by scorpio on 2018/3/7.
 */

public class DataControl {

	public void test(Object tag,JsonCallback<DetailData> callback) {
		new OkHttpUtils()
				.get()
				.url("https://m.9ji.com/web/api/products/productCityDetail/v1")
				.param("ppid","58206")
				.addHeader("City","530102")
				.tag(tag)
				.build()
				.execute(callback);
	}

}
