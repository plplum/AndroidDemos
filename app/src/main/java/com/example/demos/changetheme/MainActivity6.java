package com.example.demos.changetheme;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;

import com.example.demos.R;

/**
 * 公司：大商道商品交易市场股份有限公司
 * 项目：大商道离岸交易平台
 * 简述：XXX
 * 作者：Chenxp
 * 时间：2018/4/19
 * 版本：V1.0.0
 */
public class MainActivity6 extends AppCompatActivity {

    private SharedPreferences setting;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main6);

        setting = PreferenceManager.getDefaultSharedPreferences(this);
        // 获取当前主题
        if (setting.getBoolean("switch_nightMode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        Button button = (Button) findViewById(R.id.change_theme);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                    setting.edit().putBoolean("switch_nightMode", false).apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    setting.edit().putBoolean("switch_nightMode", true).apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                recreate();

            }
        });

    }
}
