package com.example.demos.baidumap.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public abstract class BaseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//鍘绘帀鏍囬鏍�
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//鍘绘帀淇℃伅鏍�		 
	}
	
	

}
