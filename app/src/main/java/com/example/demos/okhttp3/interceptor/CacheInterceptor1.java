package com.example.demos.okhttp3.interceptor;

import android.text.TextUtils;

import com.example.demos.city.app.Application;
import com.example.demos.util.NetUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公司：大商道商品交易市场股份有限公司
 * 项目：大商道离岸交易平台
 * 简述：XXX
 * 作者：Chenxp
 * 时间：2018/3/14
 * 版本：V1.0.0
 */
public class CacheInterceptor1 implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //离线时，可以获取缓存，在线时获取最新数据。
        if (!NetUtils.isConnected(Application.getInstance())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            //LogCat.i("no network");
        }
        Response response = chain.proceed(request);
        if (NetUtils.isConnected(Application.getInstance())) {
            int maxAge = 0 * 60; // 有网络时 设置缓存超时时间为0;
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        }/* else {
            //int maxStale = 60 * 60 * 24; // 无网络时，设置超时为1天
            int maxStale = 10; // 无网络时，设置超时为1天
            //LogCat.i("has maxStale="+maxStale);
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }*/
        return response;
    }
}
