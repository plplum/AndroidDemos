package com.example.demos.drawerlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demos.R;

public class DrawerLayoutActivity extends Activity implements OnClickListener{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private String[] mPlanetTitles = new String[] { "项目1", "项目2", "项目3", "项目4", "项目5", "项目6", "项目7", "项目8" , "项目9", "项目10"};
	private ImageButton button;
			
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawerlayout_main);

		//android:layout_gravity="left" 设置左右显示left|right
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//关闭手势滑动
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mPlanetTitles));
		
		button = (ImageButton) findViewById(R.id.titlebar_image_button);
		button.setOnClickListener(this);
		
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				mDrawerList.setItemChecked(position, true);
				mDrawerLayout.closeDrawer(mDrawerList);
				Toast.makeText(DrawerLayoutActivity.this, mPlanetTitles[position], Toast.LENGTH_SHORT).show();
			}
		});
		
		//监听按下系统返回键事件
		mDrawerLayout.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					/*
					 * if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
					 * mDrawerLayout.closeDrawer(Gravity.LEFT); }
					 */
					// 直接退出当前页面
					onBackPressed();
				}
				return false;
			}
		});
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_image_button:
			// 按钮按下，将抽屉打开
			if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			}else{
				mDrawerLayout.openDrawer(Gravity.LEFT);
			}
			break;

		default:
			break;
		}
		
	}
	
	/* @Override//关闭侧滑页面
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return true;
    }*/
	
}
