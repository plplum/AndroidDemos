package com.example.demos.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;

public class HttpDownloadActivity extends Activity {
	/** Called when the activity is first created. */
	ProgressBar pb;
	TextView tv;
	int fileSize;
	int downLoadFileSize;
	String fileEx, fileNa, filename;
	private Button button;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 定义一个Handler，用于处理下载线程与UI间通讯
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case 0:
					pb.setMax(100);
				case 1:
					int result = downLoadFileSize * 100 / fileSize;
					tv.setText(result + "%");
					pb.setProgress(result);
					break;
				/*case 0:
					pb.setMax(fileSize);
				case 1:
					pb.setProgress(downLoadFileSize);
					int result = downLoadFileSize * 100 / fileSize;
					tv.setText(result + "%");
					break;*/
				case 2:
					Toast.makeText(HttpDownloadActivity.this, "文件下载完成", 1)
							.show();
					break;

				case -1:
					String error = msg.getData().getString("error");
					Toast.makeText(HttpDownloadActivity.this, error, 1).show();
					break;
				}
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		pb = (ProgressBar) findViewById(R.id.down_pb);
		tv = (TextView) findViewById(R.id.tv);

		
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HttpDownloadActivity.this, MultipleDownloadActivity.class);
				startActivity(intent);
			}
		});
		
		final String dowloadDir = Environment.getExternalStorageDirectory()+ "/androidDemos/";

		new Thread() {
			public void run() {
				try {
					down_file(
							"http://img.wallpapersking.com/800/2015-8/2015082407374.jpg",
							//"http://down.360safe.com/se/360se7.1.1.596.exe",
							dowloadDir);
					// 下载文件，参数：第一个URL，第二个存放路径
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	public void down_file(String url, String path) throws IOException {
		filename = url.substring(url.lastIndexOf("/") + 1);
		
		//1.java实现
		/*URL myURL = new URL(url);
		URLConnection conn = myURL.openConnection();
		conn.connect();
		InputStream is = conn.getInputStream();
		this.fileSize = conn.getContentLength();// 根据响应获取文件大小
		 */
		
		//2.apache http实现
		InputStream is = null;
		try {
			HttpResponse httpResponse = null;
			// 1.通过url创建HttpGet对象
			HttpGet httpGet = new HttpGet(url);
			// 2.通过DefaultClient的excute方法执行返回一个HttpResponse对象
			HttpClient httpClient = new DefaultHttpClient();
			httpResponse = httpClient.execute(httpGet);
			// 取得HttpEntiy
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			this.fileSize = (int) httpEntity.getContentLength();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		if (this.fileSize <= 0)
			throw new RuntimeException("无法获知文件大小 ");
		if (is == null)
			throw new RuntimeException("stream is null");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String filePath = path + filename;
		FileOutputStream fos = new FileOutputStream(filePath);
		// 把数据存入路径+文件名
		byte buf[] = new byte[1024];
		downLoadFileSize = 0;
		sendMsg(0);
		
		int numread = 0;
		while((numread = is.read(buf))!=-1){
			fos.write(buf, 0, numread);
			downLoadFileSize += numread;
			sendMsg(1);// 更新进度条
		}
		
	/*	do {
			// 循环读取
			int numread = is.read(buf);
			if (numread == -1) {
				break;
			}
			fos.write(buf, 0, numread);
			downLoadFileSize += numread;

			sendMsg(1);// 更新进度条
		} while (true);*/
		sendMsg(2);// 通知下载完成
		try {
			is.close();
			fos.close();
		} catch (Exception ex) {
			Log.e("tag", "error: " + ex.getMessage(), ex);
		}

	}

	private void sendMsg(int flag) {
		Message msg = new Message();
		msg.what = flag;
		handler.sendMessage(msg);
	}

}