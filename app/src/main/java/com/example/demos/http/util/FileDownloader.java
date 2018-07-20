package com.example.demos.http.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FileDownloader {
	private static final String TAG = "FileDownloader";   //方便调试语句的编写
	private String urlStr;   //文件下载位置
	private int fileLength;  //下载文件长度
	/** 文件已下载长度  */
	private int downloadLength;	//文件已下载长度
	private File saveFile;		//SD卡上的存储文件
	private int block; 		//每个线程下载的大小
	private int threadCount = 2; //该任务的线程数目
	private DownloadThread[] loadThreads;		
	private Handler handler;
	private Boolean isFinished = false;	//该任务是否完毕
	private Boolean isPause = false;
	private Context context;
	RandomAccessFile accessFile;
	
	protected synchronized void append(int size){  //多线程访问需加锁
		this.downloadLength += size;
	}
	
	public FileDownloader(String urlStr, Handler handler,Context context){
		this.urlStr = urlStr;
		this.handler = handler;  
		this.loadThreads = new DownloadThread[threadCount];
		this.context = context;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(3000);	//设置超时
			
			if(conn.getResponseCode() == 200){
				fileLength = conn.getContentLength();	//获取下载文件长度
				String tmpFile = urlStr.substring(urlStr.lastIndexOf('/')+1);  //获取文件名
				print(tmpFile);
				saveFile = new File(Environment.getExternalStorageDirectory(), tmpFile);
				accessFile= new RandomAccessFile(saveFile,"rws");
				accessFile.setLength(fileLength);   //设置本地文件和下载文件长度相同
				accessFile.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public void Download(CompleteListener listener) throws Exception{
		
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(3000);	//设置超时
		
		if(conn.getResponseCode() == 200){

			//发送消息设置进度条最大长度
			Message msg = new Message();
			msg.what = 0;
			msg.getData().putInt("filelength", fileLength);
			handler.sendMessage(msg); 
			
			block = fileLength%threadCount == 0? fileLength/threadCount : fileLength/threadCount+1;   //计算线程下载量,每个线程从多少字节处开始下载
			print(Integer.toString(block));
			for(int i = 0 ; i < threadCount ; i++){
				this.loadThreads[i] = new DownloadThread(context,this,urlStr,saveFile,block,i);   //新建线程开始下载
				this.loadThreads[i].start();
			}

			while(!isPause && !this.isFinished){
				this.isFinished = true;   
				Thread.sleep(900);
				for(int i = 0 ; i < threadCount ; i++){
					if(loadThreads[i] != null && !loadThreads[i].isFinished()){
						this.isFinished = false;
					}
				}
				
				Message msg2 = new Message();
				msg2.what = 1;
				msg2.getData().putInt("currentlength", downloadLength);	//
				handler.sendMessage(msg2);	//发送 消息更新进度条
				//print(Integer.toString(downloadLength));
			}
			if(this.isFinished && listener != null){
					listener.isComplete(downloadLength);
			}
		}
	}
	
	private void print(String msg){    //打印提示消息
		Log.d(FileDownloader.TAG,msg);
	}
	
	public void setPause(){
		isPause = true;		//该任务暂停
		for(int i = 0 ;i < threadCount ; i++){
			if(loadThreads[i]!= null && !loadThreads[i].isFinished()){
				loadThreads[i].setPause();	//设置该线程暂停
			}
		}
	}
	
	public void setResume(final CompleteListener listener) throws Exception{
		isPause = false;
		this.downloadLength = 0;
		this.Download(new CompleteListener(){
			public void isComplete(int size) {
				listener.isComplete(size);
				print("listener");
			}
		});
	}
	public Boolean isFinished(){
		return this.isFinished;
	}
}
