package com.example.demos.http;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;
import com.example.demos.http.util.FileUtils;
import com.example.demos.http.util.UploadUtil;

public class HttpUploadActivity extends Activity implements OnClickListener{
	
	private Button buttonSelect;
	private Button buttonDownload;
	private TextView textViewFilePath;
	private TextView textViewUrlValue;
	
	
	private final int FILE_SELECT_CODE = 0;
	/** 服务器上传地址 */
	private String uploadServerUrl = "http://10.19.2.125:8080/UploadServlet/UploadServlet?";
	/** 服务器下载播放地址 */
	private String downloadServerUrl = "http://10.19.2.125:8080/UploadServlet/downloadfile?filename=";

	private UploadFileStateListener fileStateListener;
	
	private String fileName = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_http_upload);
		
		buttonSelect = (Button) findViewById(R.id.button_select);
		buttonSelect.setOnClickListener(this);
		buttonDownload = (Button) findViewById(R.id.button_download);
		buttonDownload.setOnClickListener(this);
		textViewFilePath = (TextView) findViewById(R.id.textView_pathValue);
		textViewUrlValue = (TextView) findViewById(R.id.textView_urlValue);
		fileStateListener = new UploadFileStateListener(){

			@Override
			public void onState(int resultCode, String message) {
				Toast.makeText(HttpUploadActivity.this, message, Toast.LENGTH_LONG).show();
			}
		};
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_select:
			this.showFileChooser();
			break;
		case R.id.button_download:
			new DownloadRecordFile().execute(textViewFilePath.getText().toString());
		default:
			break;
		}
		
	}

	// 打开文件选择器
	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case FILE_SELECT_CODE:
			if (resultCode == RESULT_OK) {
				// Get the Uri of the selected file
				Uri uri = data.getData();
				String path = FileUtils.getPath(this, uri);
				//String path2 = "/storage/emulated/0/360智能摄像机/微博/2015-11-03_2015-11-04t012cc0987b4b9ebae5.jpg";
				
				textViewFilePath.setText(path);
				new UpLoadecordFile().execute(path);
				//new UpLoadecordFile().execute(path2);
			}
			break;
		}
	}
	
	/**
	 * @author Administrator
	 *	异步任务上传文件
	 */
	public class UpLoadecordFile extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... parameters) {
			String path = parameters[0];
			File file = new File(path);
			fileName = file.getName();
			return UploadUtil.uploadFile(file, uploadServerUrl);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				if (fileStateListener != null)
					fileStateListener.onState(-2, "上传文件失败");
				return;
			}

			if (result != null)
				if (fileStateListener != null){
					fileStateListener.onState(0, "上传文件成功");
					textViewUrlValue.setText(downloadServerUrl + fileName);
				}
		}
	}

	/** 异步任务-下载后播放 */
	public class DownloadRecordFile extends AsyncTask<String, Integer, File> {

		@Override
		protected File doInBackground(String... parameters) {
			try {
				return FileUtils.DownloadFromUrlToData(downloadServerUrl+fileName, fileName, HttpUploadActivity.this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(File result) {
			if (result == null || !result.exists() || result.length() == 0) {
				if (fileStateListener != null) {
					fileStateListener.onState(-1, "下载文件失败");
					return;
				}
			}else if (fileStateListener != null) {
				fileStateListener.onState(0, "下载文件成功");
				return;
			}
		}

	}
	
	/**
	 * @author Administrator
	 * 文件上传状态监听器
	 */
	public interface UploadFileStateListener {
		public void onState(int resultCode, String message);
	}
	
}
