package com.example.demos.slidable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.demos.R;
import com.example.demos.actionbar.activity.SearchViewActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * 公司：大商道商品交易市场股份有限公司
 * 项目：大商道离岸交易平台
 * 简述：XXX
 * 作者：Chenxp
 * 时间：2018/4/23
 * 版本：V1.0.0
 */
public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);

        SlidrConfig config = new SlidrConfig.Builder()
                .edge(false)//true 边缘触发, false 全屏触发
                .build();

        Slidr.attach(this, config);
        //Slidr.attach(this);

        /*SlidrConfig config1 = new SlidrConfig.Builder()
                .primaryColor(getResources().getColor(R.color.gray))
                        .secondaryColor(getResources().getColor(R.color.green))
                                .position(SlidrPosition.LEFT)
                                .sensitivity(1f)
                                .scrimColor(Color.BLACK)
                                .scrimStartAlpha(0.8f)
                                .scrimEndAlpha(0f)
                                .velocityThreshold(2400)
                                .distanceThreshold(0.25f)
                                .edge(true)
                                .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
                                .build();

        SlidrInterface slidrInterface = Slidr.attach(this, config1);

        slidrInterface.lock();
        slidrInterface.unlock();*/
    }
}