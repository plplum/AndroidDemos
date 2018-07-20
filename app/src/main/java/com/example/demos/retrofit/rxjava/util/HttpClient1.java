package com.example.demos.retrofit.rxjava.util;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 公司：大商道商品交易市场股份有限公司
 * 项目：大商道离岸交易平台
 * 简述：XXX
 * 作者：Chenxp
 * 时间：2018/3/12
 * 版本：V1.0.0
 */
public class HttpClient1 {

    protected static final String TAG = HttpClient1.class.getSimpleName();

    private final static String BASE_URL = "http://fanyi.youdao.com/";
    private final static String BASE_URL1 = "http://fy.iciba.com/";


    private static Retrofit mRetrofit;

    public static void init() {
        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //自定义OkHttpClient
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        //添加拦截器
        okHttpClient.addInterceptor(httpLoggingInterceptor);
        //okHttpClient.addInterceptor(new CommonInterceptor());

        //设置超时
        okHttpClient.connectTimeout(15, TimeUnit.SECONDS);
        okHttpClient.readTimeout(20, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        okHttpClient.retryOnConnectionFailure(true);

        //创建Retrofit对象
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static Retrofit initWithHeader(){
        mRetrofit = new Retrofit.Builder()
                .client(genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL1)
                .build();
        return mRetrofit;
    }

    public static OkHttpClient genericClient() {
        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("apikey", "2ffc3e48c7086e0e1faa003d781c0e69")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        return httpClient;
    }


    /**
     * 设置通用请求参数
     */
    public static class CommonInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();

            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter("token", "test001");

            // 新的请求
            Request newRequest = oldRequest.newBuilder()
                    .header("headertest", "head001")
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .build();

            return chain.proceed(newRequest);
        }
    }

    public static Retrofit getRetrofit() {
        if (mRetrofit==null){
            init();
        }
        return mRetrofit;
    }

    public static <T> T creatService(Class<T> service) {
        if (mRetrofit==null){
            init();
        }
        return mRetrofit.create(service);
    }


}
