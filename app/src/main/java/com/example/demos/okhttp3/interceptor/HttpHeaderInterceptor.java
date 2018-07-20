package com.example.demos.okhttp3.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpHeaderInterceptor implements Interceptor {

    private static final String TAG = "HttpHeaderInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("apikey", "2ffc3e48c7086e0e1faa003d781c0e69")
                .build();
        return chain.proceed(request);
    }
}  