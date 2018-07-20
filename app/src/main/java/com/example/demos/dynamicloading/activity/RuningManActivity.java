package com.example.demos.dynamicloading.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.demos.R;
import com.example.demos.dynamicloading.dialog.CustomProgressDialog;


public class RuningManActivity extends Activity {
	private ImageView mImageView;
	private AnimationDrawable mAnimation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_runing_man);
		
		
		mImageView = (ImageView) findViewById(R.id.loadingIv);
		mImageView.setBackgroundResource(R.anim.frame);
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();

			}
		});
	}
	/**
	 * 显示美团进度对话框
	 * @param v
	 */
	public void showmeidialog(View v){
		CustomProgressDialog dialog =new CustomProgressDialog(this, "正在加载中",R.anim.frame);
		dialog.show();
	}
	/**
	 * 显示顺丰快递员进度对话框
	 * @param v
	 */
	public void showsfdialog(View v){
		CustomProgressDialog dialog =new CustomProgressDialog(this, "正在加载中",R.anim.frame2);
		dialog.show();
	}
}
