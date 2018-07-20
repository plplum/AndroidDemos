package com.example.demos.okhttp3.interceptor;

import android.text.TextUtils;

import java.io.IOException;

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
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);


        //这种情况是不管有没有网络，都先读缓存，缓存时间为60s;

        String cacheControl = request.cacheControl().toString();
        if (TextUtils.isEmpty(cacheControl)) {
            cacheControl = "public, max-age=60";
        }
        return response.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma")
                .build();
    }
}
