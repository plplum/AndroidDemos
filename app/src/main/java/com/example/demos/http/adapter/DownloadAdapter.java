package com.example.demos.http.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.http.util.CompleteListener;
import com.example.demos.http.util.FileDownloader;

public class DownloadAdapter extends BaseAdapter {
	private Context context;
	private List<String> urls;
	private LayoutInflater layoutInflater;
	private ViewHolder holder;
	private FileDownloader fileDownloader;
	private ListView listView;
	
	public DownloadAdapter(Context context, ListView listView, List<String> urls) {
		this.context = context;
		this.urls = urls;
		this.listView = listView;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return urls.size();
	}

	@Override
	public Object getItem(int position) {
		return urls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final String url = urls.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.download_item, null);
			holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
			holder.download_value_text = (TextView) convertView.findViewById(R.id.download_value_text);
			holder.download_btn = (Button) convertView.findViewById(R.id.download_btn);			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}			
		
		holder.download_btn.setOnClickListener(new MyListener(holder.progressBar ,holder.download_value_text, url));
		
		return convertView;
	}
			
	/**
     * 用于存储每一条项目所需的数据
     */
    class ViewHolder{
    	ProgressBar progressBar;   
    	TextView download_value_text;
    	Button download_btn;		
	}
        
    private final class MyListener implements OnClickListener{
		private ProgressBar pb ;
		private TextView tv;
		private String url;
		private FileDownloader fileDownloader;
		
		public MyListener(ProgressBar pb, TextView tv, String url){
			this.pb = pb;
			this.tv = tv;
			this.url = url;
		}
		
		public void onClick(View v) {
			final Button pauseButton = (Button)v;
			final Handler mainHandler = new Handler(){

				@Override
				public void handleMessage(Message msg) {
					switch(msg.what){
					case 2:
						pauseButton.setText("安装");
						break;
					}
				}
				
			};
			if(pauseButton.getText().equals("开始")){
				pauseButton.setText("暂停");
				new Thread(){
					public void run(){
						try{
							fileDownloader = new FileDownloader(url,handler,context);
							fileDownloader.Download(new CompleteListener(){
								public void isComplete(int size) {
									Message msg = new Message();
									msg.what = 2;
									mainHandler.sendMessage(msg);
								}
							});
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}.start();
			}else if(pauseButton.getText().equals("暂停")){
				fileDownloader.setPause();
				pauseButton.setText("继续");
			}else if(pauseButton.getText().equals("继续")){
				pauseButton.setText("暂停");
				new Thread(){
					public void run(){
						try{
							fileDownloader.setResume(new CompleteListener(){
								public void isComplete(int size) {
									Message msg = new Message();
									msg.what = 2;
									mainHandler.sendMessage(msg);
								}
							});
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}.start();
			}else if(pauseButton.getText().equals("安装")){
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				String installPath = Environment.getExternalStorageDirectory() + "/"+url.substring(url.lastIndexOf('/')+1);
				intent.setDataAndType(Uri.fromFile(new File(installPath)), "application/vnd.android.package-archive");
				context.startActivity(intent);
			}
		}
		
		private Handler handler = new Handler(){
			int fileLength = 1;
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
				case 0:     //得到进度条的最大长度
					fileLength = msg.getData().getInt("filelength");
					pb.setMax(fileLength);		//设置进度条最大长度
					break;
				case 1:		//设置进度条现在的长度
					int currentLength = msg.getData().getInt("currentlength");
					pb.setProgress(currentLength);		//设置当前进度条长度
					tv.setText(" 已下载："+currentLength*100 / fileLength+"%");
					if(currentLength == fileLength){
						tv.setText(" 下载完成："+currentLength*100 / fileLength+"%");
					}
					break;
				}
			}			
		};
	}
}
