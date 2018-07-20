package com.example.demos.segmentedgroup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.example.demos.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private SegmentedGroup mGroup;


    private ViewPager pager;
    private MyPagerAdapter adapter;

    private RadioButton radioButton;
    private RadioButton radioButton1;

    private Fragment1 fragment1 = new Fragment1();
    private Fragment2 fragment2 = new Fragment2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_segmentedgroup);


        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(mToolbar);
        mGroup = (SegmentedGroup) findViewById(R.id.segmentbutton);
        pager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        radioButton = (RadioButton) mGroup.findViewById(R.id.button31);
        radioButton1 = (RadioButton) mGroup.findViewById(R.id.button32);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // arg0 :当前页面，及你点击滑动的页面；arg1:当前页面偏移的百分比；arg2:当前页面偏移的像素位置
                Log.e("test", "onPageScrolled------>arg0："+position+"\nonPageScrolled------>arg1:"+positionOffset+"\nonPageScrolled------>arg2:"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                // arg0是当前选中的页面的Position
                Log.e("test", "onPageSelected------>"+position);
                mGroup.clearCheck();
                switch (position){
                    case 0:
                        radioButton.setChecked(true);
                       // radioButton1.setChecked(false);
                        break;
                    case 1:
                       // radioButton.setChecked(false);
                        radioButton1.setChecked(true);
                        break;
                }
               // mGroup.getChildAt(position).setEnabled(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //arg0 ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没做。
                if(state == 0){
                    Log.e("test", "onPageScrollStateChanged------>0");
                }else if(state == 1){
                    Log.e("test", "onPageScrollStateChanged------>1");
                }else if(state == 2){
                    Log.e("test", "onPageScrollStateChanged------>2");
                }

            }
        });
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });

      /*  mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.button31:
                        if (radioButton.isChecked())
                            pager.setCurrentItem(0);
                        break;
                    case R.id.button32:
                        if (radioButton1.isChecked())
                            pager.setCurrentItem(1);
                        break;
                }

            }
        });*/

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<String> catalogs = new ArrayList<String>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            catalogs.add("1");
            catalogs.add("2");

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return catalogs.get(position);
        }

        @Override
        public int getCount() {
            return catalogs.size();
        }

        @Override
        public Fragment getItem(int position) {
           // return NewsFragment.newInstance(position);
            if (position==0){
                return fragment1;
            }else {
                return fragment2;
            }
        }

    }

}
