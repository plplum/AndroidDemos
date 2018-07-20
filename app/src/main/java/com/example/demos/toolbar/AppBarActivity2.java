package com.example.demos.toolbar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.demos.R;

/**
 * 公司：深圳市中泰智丰物联网科技有限公司
 * 项目：智能家居APP
 * 简述：XXX页面
 * 作者：Chenxp
 * 时间：2017/7/22
 * 版本：V0.0.1
 */
public class AppBarActivity2 extends AppCompatActivity {

	AppBarLayout appBarLayout;

	LinearLayout headLayout;

	CollapsingToolbarLayout collapsingToolbarLayout;

	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main22);


		//状态栏设置透明
		//StatusBarUtil.setTransparent(AppBarActivity2.this);

        Utils.setStatusBar(AppBarActivity2.this, false, true);


		toolbar = (Toolbar) findViewById(R.id.toolbar);

		//toolbar.setTitle("This is Title");
		//toolbar.setNavigationIcon(R.drawable.back);
		setSupportActionBar(toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("1111222222222");
			}
		});


		appBarLayout = (AppBarLayout) findViewById(R.id.AppBarLayout01);

		headLayout = (LinearLayout) findViewById(R.id.head_layout);

		collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collLayout);

		setTitleToCollapsingToolbarLayout();
/*
		ImageView imageView = (ImageView) findViewById(R.id.back);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("2b");
			}
		});


		TextView textView = (TextView) findViewById(R.id.toolbar_edit);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("3333333333333");
			}
		});*/
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		//作用同setNavigationOnClickListener
		if (item.getItemId() == R.id.back){
			System.out.println("1111222222222");
		}
		return super.onOptionsItemSelected(item);

	}*/

	/**
	 * 使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，
	 * 设置到Toolbar上则不会显示
	 */
	private void setTitleToCollapsingToolbarLayout() {
		appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				if (verticalOffset <= -headLayout.getHeight() / 2) {
					collapsingToolbarLayout.setTitle("This is Title");
					//使用下面两个CollapsingToolbarLayout的方法设置展开透明->折叠时你想要的颜色
					collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
					//collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
				} else {
					collapsingToolbarLayout.setTitle("");
				}
			}
		});
	}




}
