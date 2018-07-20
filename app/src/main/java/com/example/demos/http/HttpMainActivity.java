package com.example.demos.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.demos.R;
import com.example.demos.http.util.HttpUtil;
import com.example.demos.http.util.HttpUtilApache;
import com.example.demos.util.HttpUtils;
import com.example.demos.util.NetUtils;
import com.example.demos.util.ToastUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class HttpMainActivity extends Activity implements OnClickListener{
	
	private Button requestButton;
	private EditText urlEditText;
	private EditText resultEditText;
	private String textUrl;
	private String imageUrl;
	private Dialog mLoadingDialog;
	
	private ImageView mImageView;
	private AnimationDrawable mAnimation;
	
	private ImageView imageView1;
	
	private Button button;
	private Button buttonXUtil;
	private Button buttonUpload;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http);
		
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		
		requestButton = (Button) findViewById(R.id.button_request);
		urlEditText = (EditText) findViewById(R.id.editText_url);
		resultEditText = (EditText) findViewById(R.id.editText_result);
		
		buttonXUtil =  (Button) findViewById(R.id.button_xutil);
		buttonXUtil.setOnClickListener(this);
		
		requestButton.setOnClickListener(this);
		
		urlEditText.setText("http://hq.sinajs.cn/list=sh601006");
		textUrl = urlEditText.getText().toString();
		imageUrl = "http://image.sinajs.cn/newchart/daily/n/sh601006.gif";
		
		mLoadingDialog = new Dialog(HttpMainActivity.this, R.style.loadingProgressDialog);
		mLoadingDialog.setContentView(R.layout.loading_progress_bar);
		mLoadingDialog.setCancelable(false);
		
		/*mImageView = (ImageView) mLoadingDialog.findViewById(R.id.loadingIv);
		mImageView.setBackgroundResource(R.anim.frame);
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();
			}
		});*/
		
		
		button = (Button) findViewById(R.id.button01);
		button.setOnClickListener(this);
		
		
		buttonUpload = (Button) findViewById(R.id.button_upload);
		buttonUpload.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_request:
			boolean isConnected = NetUtils.isConnected(HttpMainActivity.this);
			if(!isConnected){
				ToastUtil.showShort(HttpMainActivity.this, "网络不通请检查！");
				NetUtils.openSetting(HttpMainActivity.this);
				break;
			}
			HttpRequestTask httpRequestTask = new HttpRequestTask();
			mLoadingDialog.show();
			httpRequestTask.execute(textUrl);
			
			HttpRequestImageTask httpRequestImageTask = new HttpRequestImageTask();
			mLoadingDialog.show();
			
			//可使用1. handler 和 2. AsyncTask
			//httpRequestImageTask.execute(imageUrl);
			//handler.post(runnable); 会出异常
		    new Thread(runnable).start();
			break;
		case R.id.button01:
			Intent intent = new Intent(HttpMainActivity.this, HttpDownloadActivity.class);
			startActivity(intent);
			break;
		case R.id.button_upload:
			intent = new Intent(HttpMainActivity.this, HttpUploadActivity.class);
			startActivity(intent);
			break;
		case R.id.button_xutil:
			HttpReqXUtil();
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * xUtil http请求
	 */
	private void HttpReqXUtil (){
		com.lidroid.xutils.HttpUtils httpUtils = new com.lidroid.xutils.HttpUtils();
		httpUtils.send(HttpRequest.HttpMethod.GET, "http://hq.sinajs.cn/list=sh601006", new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				ToastUtil.showShort(HttpMainActivity.this, arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				ToastUtil.showShort(HttpMainActivity.this, arg0.result);
			}
			
			
		});
	}
	
	
	
	
	
	
	/**
	 * @author Administrator
	 * 异步执行http请求和更新UI界面
	 */
	class HttpRequestTask extends AsyncTask<String, Integer, String>{
		
		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			String result;
			try {
				//两种方法：1.使用java HttpURLConnection
				//result = HttpUtil.doGetByHttp(url);
				//2. 使用apache第三方包
				result = HttpUtilApache.request("GET", url);
			} catch (Exception e) {
				result = "";
				e.printStackTrace();
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			mLoadingDialog.dismiss();
			super.onPostExecute(result);
			resultEditText.setText(result);
		}
	}
	
	
	/**
	 * @author Administrator
	 * 异步执行http请求和更新UI界面
	 */
	class HttpRequestImageTask extends AsyncTask<String, Integer, Bitmap>{
		
		@Override
		protected Bitmap doInBackground(String... params) {
			String url = params[0];
			InputStream is;
			Bitmap bitmap = null;
			try {
				//两种方法：1.使用java HttpURLConnection
				//is = HttpUtil.getInputStream(url);
				//2. 使用apache第三方包
				is = HttpUtilApache.getInputStream(url);
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (Exception e) {
				is = null;
				e.printStackTrace();
			}
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			mLoadingDialog.dismiss();
			super.onPostExecute(bitmap);
			//set image
			imageView1.setImageBitmap(bitmap);
		}
	}
	
	Handler handler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	       // Bundle data = msg.getData();
	        Bitmap bitmap = (Bitmap) msg.obj;
			imageView1.setImageBitmap(bitmap);
	    }
	};

	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
	    	InputStream is;
	    	Bitmap bitmap = null;
			try {
				//两种方法：1.使用java HttpURLConnection
				//is = HttpUtil.getInputStream(imageUrl);
				//2. 使用apache第三方包
				is = HttpUtilApache.getInputStream(imageUrl);
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (Exception e) {
				is = null;
				e.printStackTrace();
			}
	        Message msg = new Message();
	        msg.obj = bitmap;
	        /*Bundle data = new Bundle();
	        data.putByteArray("value", array);
	        msg.setData(data);*/
	        handler.sendMessage(msg);
	    }
	};
	
}
