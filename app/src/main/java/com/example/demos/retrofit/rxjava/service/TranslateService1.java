package com.example.demos.retrofit.rxjava.service;


import com.example.demos.retrofit.Main2Activity;
import com.example.demos.retrofit.bean.Translation;
import com.example.demos.retrofit.rxjava.bean.LoginParams;
import com.example.demos.retrofit.rxjava.bean.Translation2;
import com.example.demos.retrofit.rxjava.bean.Translation3;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 公司：大商道商品交易市场股份有限公司
 * 项目：大商道离岸交易平台
 * 简述：XXX
 * 作者：Chenxp
 * 时间：2018/3/12
 * 版本：V1.0.0
 */
public interface TranslateService1 {


    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<Translation2> getTranlateResult();

    //传入请求参数
    @GET("ajax.php?a=fy&f=auto&t=auto")
    Observable<Translation2> getTranlateResult(@Query("w") String w);


    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    //@Headers("t:1")
    Observable<Translation3> getTranlateResultPost(@Field("i") String targetSentence);

    @POST("translate")
    Observable<Translation3> getTranlateResultPost2(@Body LoginParams loginParams);


}
