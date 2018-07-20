package com.example.demos.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import com.example.demos.R;

public class MainActivity extends Activity {

    private MyCountDownTimer mc;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splashscreen);
        tv = (TextView) findViewById(R.id.copy_right);
        mc = new MyCountDownTimer(4000, 1000);
        mc.start();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, com.example.demos.MainActivity.class);
                startActivity(intent);
            }
        }, 4000);
    }

    private Handler handler = new Handler();


    /**
     * 继承 CountDownTimer 防范
     * <p>
     * 重写 父类的方法 onTick() 、 onFinish()
     */

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            tv.setText("正在跳转");
        }

        public void onTick(long millisUntilFinished) {
            tv.setText("倒计时(" + millisUntilFinished / 1000 + ")");
        }

    }

}