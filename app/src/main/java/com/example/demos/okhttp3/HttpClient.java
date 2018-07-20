package com.example.demos.okhttp3;

import android.content.Context;
import android.os.Environment;

import com.example.demos.okhttp3.interceptor.CacheInterceptor;
import com.example.demos.okhttp3.interceptor.CacheInterceptor1;
import com.example.demos.okhttp3.interceptor.HttpHeaderInterceptor;
import com.example.demos.okhttp3.interceptor.LoggingInterceptor;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 公司：大商道商品交易市场股份有限公司
 * 项目：大商道离岸交易平台
 * 简述：XXX
 * 作者：Chenxp
 * 时间：2018/3/14
 * 版本：V1.0.0
 */
public class HttpClient {


    private static HttpClient mInstance = new HttpClient();

    private OkHttpClient okHttpClient;


    private HttpClient(){

    }

    public static HttpClient getInstance(){
        return mInstance;
    }

    public OkHttpClient getOkHttpClient(){
        if (okHttpClient == null) throw new NullPointerException("okHttpClient == null.");
        return okHttpClient;
    }

    public void init(Context context){
        File cacheDir = new File(getDiskCacheDir(context), "okhttp_cache");
        //如果服务器支持缓存，请求返回的Response会带有这样的Header:Cache-Control, max-age=xxx,这种情况下我们只需要手动给okhttp设置缓存就可以让okhttp自动帮你缓存了。这里的max-age的值代表了缓存在你本地存放的时间。
        Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);
        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5*1000, TimeUnit.MILLISECONDS) //链接超时
                .readTimeout(10*1000,TimeUnit.MILLISECONDS) //读取超时
                .writeTimeout(10*1000,TimeUnit.MILLISECONDS) //写入超时
                .addInterceptor(new HttpHeaderInterceptor()) //应用拦截器：统一添加消息头
                //.addNetworkInterceptor(new NetworkspaceInterceptor())//网络拦截器
                .addInterceptor(new LoggingInterceptor())//应用拦截器：打印日志
                //.addInterceptor(httpLoggingInterceptor)//google 官方log拦截器
                .addNetworkInterceptor(new CacheInterceptor1())
                .cache(cache)  //设置缓存
                .build();
    }






    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

}
