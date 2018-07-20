package com.example.demos.statusbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.demos.R;

/**
 * 公司：深圳市中泰智丰物联网科技有限公司
 * 项目：智能家居APP
 * 简述：XXX页面
 * 作者：Chenxp
 * 时间：2017/7/20
 * 版本：V0.0.1
 */
public class StatusBarActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent_demo);

        findViewById(R.id.btn_image_translucent).setOnClickListener(this);
        findViewById(R.id.btn_color_translucent).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_image_translucent) {
            Intent translucentBarIntent = new Intent(this, ImageTranslucentBarActivity.class);
            startActivity(translucentBarIntent);

        } else if (viewId == R.id.btn_color_translucent) {
            Intent translucentBarIntent = new Intent(this, ColorTranslucentBarActivity.class);
            startActivity(translucentBarIntent);

        }
    }
}
