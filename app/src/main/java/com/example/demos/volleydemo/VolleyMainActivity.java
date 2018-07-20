package com.example.demos.volleydemo;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demos.R;

public class VolleyMainActivity extends Activity implements OnClickListener{

	public static final String TAG = "VolleyMainActivity";

	private RequestQueue requestQueue;
	
	private EditText editTextUrl;
	private EditText editTextResult;
	private String url = "http://www.baidu.com";
	
	private Button buttonRequest;
	private ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_http_volley);
		
		editTextUrl = (EditText) findViewById(R.id.editText_url);
		editTextUrl.setText(url);
		editTextResult = (EditText) findViewById(R.id.editText_result);
		buttonRequest = (Button) findViewById(R.id.button_request);
		buttonRequest.setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.imageView1);
		
		requestQueue = Volley.newRequestQueue(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_request:
			url = editTextUrl.getText().toString();
			//StringRequest stringRequest = new StringRequest("http://www.baidu.com", 默认Get请求
			StringRequest stringRequest = new StringRequest(Method.GET, url,
					new Listener<String>() {

						@Override
						public void onResponse(String response) {
							Log.d(TAG, response);
							editTextResult.setText(response);
						}
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e(TAG, error.getMessage(), error);
						}
					});
			stringRequest.setTag("StringReq");
			/*StringRequest stringRequest2 = new StringRequest(Method.GET, "http://www.163.com",
					new Listener<String>() {

						@Override
						public void onResponse(String response) {
							Log.d(TAG, response);
						}
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e(TAG, error.getMessage(), error);
						}
					});*/
			
			
			//json请求
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.4.255.255", null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							String retValue = null;
							try {
								retValue = (String) response.get("country");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Log.d(TAG, retValue + "----" + response.toString());
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e(TAG, error.getMessage(), error);
						}
					});
			
			
			//1. ImageRequest图片请求
			ImageRequest imageRequest = new ImageRequest("http://image.sinajs.cn/newchart/daily/n/sh601006.gif",
					new Response.Listener<Bitmap>() {
						@Override
						public void onResponse(Bitmap response) {
							imageView.setImageBitmap(response);
						}
					}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							imageView.setImageResource(R.drawable.ic_launcher);
						}
					});
			
			
			//2. imageloader图片请求
			//此演示例子每次创建ImageLoader和BitmapCache，所以拿不到缓存图片  应该只创建一个它们的实例
			ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
			ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.ic_launcher, R.drawable.ic_launcher);  
			//imageLoader.get("http://image.sinajs.cn/newchart/daily/n/sh601006.gif", listener, 200, 300);  
			//imageloader请求网络图片
			imageLoader.get("http://image.sinajs.cn/newchart/daily/n/sh601006.gif", listener);
			
			//3. NetworkImageView 图片请求  暂时不写例子了
			
			
			
			//xml 请求 
			XMLRequest xmlRequest = new XMLRequest("http://flash.weather.com.cn/wmaps/xml/china.xml", 
					new Listener<XmlPullParser>() {

						@Override
						public void onResponse(XmlPullParser response) {
							try {
								int eventType = response.getEventType();
								while (eventType != XmlPullParser.END_DOCUMENT) {
									switch (eventType) {
									case XmlPullParser.START_TAG:
										String nodeName = response.getName();
										if ("city".equals(nodeName)) {
											String pName = response.getAttributeValue(0);
											Log.d("TAG", "pName is " + pName);
										}
										break;
									}
									eventType = response.next();
								}
							} catch (XmlPullParserException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					},
					new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e(TAG, error.getMessage(), error);  
						}
					});
			
			//请求在子线程中执行
			requestQueue.add(stringRequest);
			//imageRequest请求图片
			//requestQueue.add(imageRequest);
			requestQueue.add(jsonObjectRequest);
			//执行多个请求直接add
			//requestQueue.add(stringRequest2);
			requestQueue.add(xmlRequest);
			
			//post请求用法：
		/*	StringRequest stringPostRequest = new StringRequest(Method.POST, url,  listener, errorListener) {  
			    @Override  
			    protected Map<String, String> getParams() throws AuthFailureError {  
			        Map<String, String> map = new HashMap<String, String>();  
			        map.put("params1", "value1");  
			        map.put("params2", "value2");  
			        return map;  
			    }  
			}; */ 
			break;

		default:
			break;
		}
	}
	

	
	/**
	 * @author Administrator
	 * 图片缓存
	 */
	public class BitmapCache implements ImageCache {

		private LruCache<String, Bitmap> mCache;

		public BitmapCache() {
			//缓存大小10M
			int maxSize = 10 * 1024 * 1024;
			mCache = new LruCache<String, Bitmap>(maxSize) {
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return bitmap.getRowBytes() * bitmap.getHeight();
				}
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			return mCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			mCache.put(url, bitmap);
		}

	}
	
	
	@Override
	protected void onDestroy() {
		requestQueue.cancelAll("StringReq");
		super.onDestroy();
	}
	
}
