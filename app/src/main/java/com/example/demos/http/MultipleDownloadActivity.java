package com.example.demos.http;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.demos.R;
import com.example.demos.http.adapter.DownloadAdapter;

public class MultipleDownloadActivity extends Activity{
	
	private ListView listView;
	private DownloadAdapter downloadAdapter;
	private List<String> urls;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiple_download);

		listView = (ListView) this.findViewById(R.id.listView);
		urls = new ArrayList<String>();
		urls.add("http://gdown.baidu.com/data/wisegame/459363e233ba00a9/AnZhi_4410.apk");
		urls.add("http://gdown.baidu.com/data/wisegame/225216a70406886d/baidushoujiweishi_880.apk");
		urls.add("http://gdown.baidu.com/data/wisegame/c690abd202436bde/GOLauncherSecurity_316.apk");
		
		downloadAdapter = new DownloadAdapter(MultipleDownloadActivity.this, listView, urls);
		listView.setAdapter(downloadAdapter);
	
	}
	
}
