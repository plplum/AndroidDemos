package com.example.demos.toolbar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demos.R;

/**
 * 公司：深圳市中泰智丰物联网科技有限公司
 * 项目：智能家居APP
 * 简述：XXX页面
 * 作者：Chenxp
 * 时间：2017/7/22
 * 版本：V0.0.1
 */
public class AppBarActivity3 extends AppCompatActivity {

	AppBarLayout appBarLayout;

	LinearLayout headLayout;

	CollapsingToolbarLayout collapsingToolbarLayout;

	Toolbar toolbar;


	TextView textView;

	private TabLayout mTabLayout;
	private ViewPager mViewPager;

	private Fragment[] mFragmentArrays = new Fragment[2];
	private String[] mTabTitles = new String[2];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main33);


		//状态栏设置透明
		//StatusBarUtil.setTransparent(AppBarActivity2.this);

        Utils.setStatusBar(AppBarActivity3.this, false, true);


		toolbar = (Toolbar) findViewById(R.id.toolbar);

		//toolbar.setTitle("This is Title");
		//toolbar.setNavigationIcon(R.drawable.back);
		setSupportActionBar(toolbar);
		//toolbar.setNavigationOnClickListener(v -> System.out.println("1111222222222"));



		textView = (TextView) findViewById(R.id.toolbar_edit);
        //textView.setOnClickListener(v -> System.out.println("33333333333333333333333")
        //);




		appBarLayout = (AppBarLayout) findViewById(R.id.AppBarLayout01);

		headLayout = (LinearLayout) findViewById(R.id.head_layout);

		collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collLayout);

		setTitleToCollapsingToolbarLayout();



		mTabTitles[0] = "Menu11";
		mTabTitles[1] = "Menu22";

		mFragmentArrays[0] = AuthorInfoFragment.newInstance();
		mFragmentArrays[1] = AuthorInfoFragment.newInstance();



		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		PagerAdapter pagerAdapter = new AuthorPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(pagerAdapter);

		//初始化TabLayout，增加Tab，同时关联ViewPager
		mTabLayout = (TabLayout) findViewById(R.id.tabs);
		mTabLayout.setupWithViewPager(mViewPager);



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

		//collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.black));
		//collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));


		/*appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				if (verticalOffset <= -headLayout.getHeight() / 2) {
					collapsingToolbarLayout.setTitle("This is Title 1");
					//使用下面两个CollapsingToolbarLayout的方法设置展开透明->折叠时你想要的颜色
					//collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
					//collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
				} else {
					collapsingToolbarLayout.setTitle("This is Title 2");
					//collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));

				}
			}
		});*/
	}


	private class AuthorPagerAdapter extends FragmentPagerAdapter {

		public AuthorPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentArrays[position];
		}

		@Override
		public int getCount() {
			return mFragmentArrays.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mTabTitles[position];
		}
	}

}
