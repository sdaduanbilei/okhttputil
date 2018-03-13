package com.scorpio.drive;

import android.app.Application;

import com.scorpio.baselib.http.OkHttpUtils;
import com.scorpio.baselib.http.interceptor.AddCookiesInterceptor;
import com.scorpio.baselib.http.interceptor.ReceivedCookiesInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by sdaduanbilei on 18-3-13.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient =  new OkHttpClient().newBuilder().addInterceptor(new ReceivedCookiesInterceptor(getApplicationContext()))
                .addInterceptor(new AddCookiesInterceptor(getApplicationContext()))
                .addInterceptor(new HeaderInterceptor()).build();
        new OkHttpUtils().initClient(okHttpClient,getApplicationContext());
    }
}
