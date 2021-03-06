package com.example.demos.retrofit.rxjava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.CallSuper;
import android.util.Log;
import android.widget.Toast;

import com.example.demos.retrofit.rxjava.bean.BaseBean;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<T> {

    private final String TAG = BaseObserver.class.getSimpleName();


    private final int RESPONSE_CODE_OK = 0;
    private final int RESPONSE_CODE_FAILED = -1;

    //是否需要显示提示对话框
    private boolean showProgress = true;
    private Context mContext;

    /**
     * 根据具体的Api 业务逻辑去重写 onSuccess 方法！
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * @param mContext
     * @param showProgress 默认需要显示进程，不要的话请传 false
     */
    public BaseObserver(Context mContext, boolean showProgress) {
        this.showProgress = showProgress;
        this.mContext = mContext;
        if (showProgress) {
           // HttpUiTips.showDialog(mContext, true, null);
        }
    }

    @Override
    public final void onSubscribe(Disposable d) {
    }

    @Override
    public final void onNext(T response) {
        Log.e(TAG, response.toString());
        //HttpUiTips.dismissDialog(mContext);
        /*if (response.getCode() == RESPONSE_CODE_OK) {
            onSuccess(response.getResult());
        } else {
            onFailure(response.getCode(), response.getError());
        }*/
        onSuccess(response);
    }

    @Override
    public final void onError(Throwable t) {
        //HttpUiTips.dismissDialog(mContext);
        int code = 0;
        String errorMessage = "未知错误";
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            String meg = httpException.response().toString();   //
            code = httpException.code();
            errorMessage = httpException.getMessage();
        } else if (t instanceof SocketTimeoutException) {  //VPN open
            code = RESPONSE_CODE_FAILED;
            errorMessage = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            code = RESPONSE_CODE_FAILED;
            errorMessage = "网络连接异常，请检查网络";
        } else if (t instanceof RuntimeException) {
            code = RESPONSE_CODE_FAILED;
            errorMessage = "运行时错误";
        } else if (t instanceof UnknownHostException) {
            code = RESPONSE_CODE_FAILED;
            errorMessage = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            code = RESPONSE_CODE_FAILED;
            errorMessage = "未知的服务器错误";
        } else if (t instanceof IOException) {
            code = RESPONSE_CODE_FAILED;
            errorMessage = "没有网络，请检查网络连接";
        }

        /**
         * 严重的错误弹出dialog，一般的错误就只要Toast
         */
        /*if (RESPONSE_CODE_FAILED == code) {
            onFailure(RESPONSE_CODE_FAILED, errorMessage);
        } else {
            if (mContext != null && !((Activity) mContext).isFinishing()) {
                Toast.makeText(mContext, errorMessage + " - " + code, Toast.LENGTH_SHORT).show();
            }
        }*/

        Toast.makeText(mContext, errorMessage + " ,error code =  " + code, Toast.LENGTH_SHORT).show();
    }


    /**
     * 简单的把Dialog 处理掉
     */
    @Override
    public final void onComplete() {
//        HttpUiTips.dismissDialog(mContext);
    }

    /**
     * Default error dispose!
     * 一般的就是 AlertDialog 或 SnackBar
     *
     * @param code
     * @param message
     */
    @CallSuper  //if overwrite,you should let it run.
    public void onFailure(int code, String message) {
        if (code == RESPONSE_CODE_FAILED && mContext != null) {
           // HttpUiTips.alertTip(mContext, message, code);
        } else {
            disposeEorCode(message, code);
        }
    }


    /**
     * 对通用问题的统一拦截处理
     *
     * @param code
     */
    private final void disposeEorCode(String message, int code) {
        switch (code) {
            case 101:
            case 112:
            case 123:
            case 401:
                //退回到登录页面

                break;
        }
        Toast.makeText(mContext, message + " # " + code, Toast.LENGTH_SHORT).show();
    }

}
