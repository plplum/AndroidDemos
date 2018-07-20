package com.example.demos.toolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.demos.R;

/**
 * 公司：深圳市中泰智丰物联网科技有限公司
 * 项目：智能家居APP
 * 简述：XXX页面
 * 作者：Chenxp
 * 时间：2017/7/22
 * 版本：V0.0.1
 */
public class AppBarActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appbar_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		toolbar.setTitle("This is Title");
		toolbar.setNavigationIcon(R.drawable.back);
		setSupportActionBar(toolbar);
	}
}
