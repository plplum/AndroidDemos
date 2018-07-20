package com.example.demos.toolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.example.demos.R;

/**
 * 公司：深圳市中泰智丰物联网科技有限公司
 * 项目：智能家居APP
 * 简述：XXX页面
 * 作者：Chenxp
 * 时间：2017/8/15
 * 版本：V0.0.1
 */
public class AppBarLayoutActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private Fragment[] mFragmentArrays = new Fragment[8];
    private String[] mTabTitles = new String[8];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bar_tab_demo);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        //Navigation button是否显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTabTitles[0] = "Menu1";
        mTabTitles[1] = "Menu2";
        mTabTitles[2] = "Menu3";
        mTabTitles[3] = "Menu4";
        mTabTitles[4] = "Menu5";
        mTabTitles[5] = "Menu6";
        mTabTitles[6] = "Menu7";
        mTabTitles[7] = "Menu8";

        mFragmentArrays[0] = AuthorInfoFragment.newInstance();
        mFragmentArrays[1] = AuthorInfoFragment.newInstance();
        mFragmentArrays[2] = AuthorInfoFragment.newInstance();
        mFragmentArrays[3] = AuthorInfoFragment.newInstance();
        mFragmentArrays[4] = AuthorInfoFragment.newInstance();
        mFragmentArrays[5] = AuthorInfoFragment.newInstance();
        mFragmentArrays[6] = AuthorInfoFragment.newInstance();
        mFragmentArrays[7] = AuthorInfoFragment.newInstance();


        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new AuthorPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);

        //初始化TabLayout，增加Tab，同时关联ViewPager
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
       // mTabLayout.setTabsFromPagerAdapter(pagerAdapter);
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
