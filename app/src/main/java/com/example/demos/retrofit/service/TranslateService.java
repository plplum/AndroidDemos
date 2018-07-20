package com.example.demos.retrofit.service;

import com.example.demos.retrofit.bean.Translation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 公司：大商道商品交易市场股份有限公司
 * 项目：大商道离岸交易平台
 * 简述：XXX
 * 作者：Chenxp
 * 时间：2018/3/12
 * 版本：V1.0.0
 */
public interface TranslateService {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();

    //传入请求参数
    @GET("ajax.php?a=fy&f=auto&t=auto")
    Call<Translation> getCall(@Query("w") String w);

}
