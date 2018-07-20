package com.example.demos.progresswebview.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.demos.R;

/**
 * 带进度条的WebView
 * 
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

	private ProgressBar progressbar;
	
	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		//设置滚动条显示位置
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 6, 0, 0));
		//progressbar.setBackgroundResource(R.drawable.progressbar_drawable);
		/*
		//设置滚动条的颜色(颜色渐变)
		Drawable drawable = this.getResources().getDrawable(R.drawable.progressbar_drawable);
		progressbar.setIndeterminateDrawable(drawable);
		progressbar.setIndeterminate(true);*/
		
		//加入滚动条
		addView(progressbar);
		setWebChromeClient(new WebChromeClient());
		//如果不setWebViewClient则会直接打开第三方浏览器
		setWebViewClient(new WebViewClient(){
		});
	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//点击返回按钮时回到上一页面
			if (this.canGoBack()) {
				this.goBack();
				return true;
			} else {
				if (event.getRepeatCount() == 0) {
					return false;
				}
			}
		}
		return false;
	}
 
}