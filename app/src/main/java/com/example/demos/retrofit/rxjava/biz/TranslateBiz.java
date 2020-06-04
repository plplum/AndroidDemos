package com.example.demos.retrofit.rxjava.biz;

import com.example.demos.retrofit.rxjava.BaseObserver;
import com.example.demos.retrofit.rxjava.bean.Translation2;
import com.example.demos.retrofit.rxjava.bean.Translation3;
import com.example.demos.retrofit.rxjava.service.TranslateService1;
import com.example.demos.retrofit.rxjava.util.HttpClient1;
import com.example.demos.retrofit.util.HttpClient;

//import org.reactivestreams.Subscriber;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 公司：大商道商品交易市场股份有限公司
 * 项目：大商道离岸交易平台
 * 简述：XXX
 * 作者：Chenxp
 * 时间：2018/3/12
 * 版本：V1.0.0
 */
public class TranslateBiz {

    //Rxjava 1.x
    /*public void getTranlateResult(Subscriber<Translation2> subscriber) {
        HttpClient1.creatService(TranslateService1.class).getTranlateResult()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getTranlateResultPost(Subscriber<Translation3> subscriber, String str) {
        HttpClient1.creatService(TranslateService1.class).getTranlateResultPost(str)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }*/


    //Rxjava2.x

    public void getTranlateResult(Observer<Translation2> observer) {
        HttpClient1.creatService(TranslateService1.class).getTranlateResult()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getTranlateResultPost1(Observer<Translation3> observer, String str) {
        HttpClient1.creatService(TranslateService1.class).getTranlateResultPost(str)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getTranlateResultPost(BaseObserver<Translation3> observer, String str) {
        HttpClient1.creatService(TranslateService1.class).getTranlateResultPost(str)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
