package com.example.demos.progresswebview.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.progresswebview.view.ProgressWebView;

/**
 * 加载网页的Activity
 * 
 */
public class WebActivity extends Activity implements OnClickListener{

    private ProgressWebView webview;
    private String url;
    private String name;
    private TextView titleText;
    private ImageView imageViewCancel;
    private ImageView imageViewRefresh;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progresswebview);

        // 获取参数
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");

        webview = (ProgressWebView) findViewById(R.id.webview);
        titleText = (TextView) findViewById(R.id.title_name);
        titleText.setText(name);
        webview.getSettings().setJavaScriptEnabled(true);
        //下载监听：下载时候打开第三方浏览器下载
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        webview.loadUrl(url);
        
        imageViewCancel = (ImageView) findViewById(R.id.btn_cancel);
        imageViewCancel.setOnClickListener(this);
        imageViewRefresh = (ImageView) findViewById(R.id.btn_refresh);
        imageViewRefresh.setOnClickListener(this);
        
    }
    
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.btn_cancel:
			this.finish();
			break;
		case R.id.btn_refresh:
			webview.reload();
			break;
		default:
			break;
		}
    	
    }
}