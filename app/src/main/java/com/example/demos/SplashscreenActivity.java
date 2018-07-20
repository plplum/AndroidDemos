package com.example.demos;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;

public class SplashscreenActivity extends Activity implements DialogInterface.OnClickListener{
	private Animation endAnimation;
	private Handler endAnimationHandler;
	private Runnable endAnimationRunnable;
	public final static String FIRST_RUN_PREFERENCE = "first_run";
	private boolean mIsFirstRun = true;
    private Dialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashscreen);
	    findViewById(R.id.splashlayout);
	   /* 
	    //1. 第一种实现方法： 几秒后自动跳转到另外页面
	    new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(SplashscreenActivity.this, MainActivity.class);
				startActivity(intent);
				SplashscreenActivity.this.finish();
				//淡入淡出动画
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		}, 3000);*/
	    
	   /* //2. 第二种实现方法：动画结束后跳转到另外页面
		endAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		endAnimation.setFillAfter(true);
		   
		endAnimationHandler = new Handler();
		endAnimationRunnable = new Runnable() {
			@Override
			public void run() {
				findViewById(R.id.splashlayout).startAnimation(endAnimation);
			}
		};
		
		endAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {	}
			
			@Override
			public void onAnimationRepeat(Animation animation) { }
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//如果第一次运行，则进入系统引导页面，不是第一次运行，以后直接进入主界面，为了测试这里没实现。
				Intent intent = new Intent(SplashscreenActivity.this, MainActivity.class);
				//Intent intent = new Intent(SplashscreenActivity.this, ContactsActivity.class);
				startActivity(intent);
				SplashscreenActivity.this.finish();
			}
		});   
		
		mIsFirstRun = PreferenceManager.getDefaultSharedPreferences(SplashscreenActivity.this).getBoolean(FIRST_RUN_PREFERENCE, true);
		//showTutorial();
		runAnimaHandler();*/
	    
	    
	    //3. 第三种实现方法：在task处理完业务后在onPostExecute中跳转到另外页面
	    BusinessTask businessTask = new BusinessTask();
	    businessTask.execute("test");
		
	}
	
	/**
	 * show the tutorial dialog when the application runs at the first time
	 */
	private final void showTutorial() {
		//boolean showTutorial = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(FIRST_RUN_PREFERENCE, true);
		boolean showTutorial = false;
		if (showTutorial) {
			final TutorialDialog dlg = new TutorialDialog(this);
			dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					CheckBox cb = (CheckBox) dlg.findViewById(R.id.checkBox1);
					if (cb != null && cb.isChecked()) {
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashscreenActivity.this);
						prefs.edit().putBoolean(FIRST_RUN_PREFERENCE, false).commit();
					}
					endAnimationHandler.removeCallbacks(endAnimationRunnable);
					endAnimationHandler.postDelayed(endAnimationRunnable, 2000);
				}
			});
			dlg.show();
		} else {
			endAnimationHandler.removeCallbacks(endAnimationRunnable);
			endAnimationHandler.postDelayed(endAnimationRunnable, 1500);
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			mDialog.dismiss();
			//installShortCut();
			//addSelfShortCut(SplashscreenActivity.this);
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			mDialog.dismiss();
			break;
		default:
			break;
		}
		runAnimaHandler();
	}
	
	private void runAnimaHandler(){
		if(mIsFirstRun){
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashscreenActivity.this);
			prefs.edit().putBoolean(FIRST_RUN_PREFERENCE, false).commit();
		}
		
		endAnimationHandler.removeCallbacks(endAnimationRunnable);
		endAnimationHandler.postDelayed(endAnimationRunnable, 1500);
	}
	
	
	class BusinessTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			//do something here 
			//假设3秒处理完业务
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "testData";
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if ("testData".equals(result)) {
				Intent intent = new Intent(SplashscreenActivity.this, MainActivity.class);
				startActivity(intent);
				SplashscreenActivity.this.finish();
				//淡入淡出动画
				//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			}
			
		}
		
	}
	
}