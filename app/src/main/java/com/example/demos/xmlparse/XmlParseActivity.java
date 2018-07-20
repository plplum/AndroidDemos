package com.example.demos.xmlparse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.xmlparse.bean.River;
import com.example.demos.xmlparse.util.PullParseXml;

/**
 * 几种解析技术的比较与总结：
 * 
 * 对于Android的移动设备而言，因为设备的资源比较宝贵，内存是有限的，所以我们需要选择适合的技术来解析XML，这样有利于提高访问的速度。
 * 
 * 1 DOM在处理XML文件时，将XML文件解析成树状结构并放入内存中进行处理。当XML文件较小时，我们可以选DOM，因为它简单、直观。
 * 
 * 2 SAX则是以事件作为解析XML文件的模式，它将XML文件转化成一系列的事件，由不同的事件处理器来决定如何处理。XML文件较大时，
 * 选择SAX技术是比较合理的。虽然代码量有些大，但是它不需要将所有的XML文件加载到内存中。这样对于有限的Android内存更有效，
 * 而且Android提供了一种传统的SAX使用方法以及一个便捷的SAX包装器。 使用Android．util．Xml类，从示例中可以看出，会比使用
 * SAX来得简单。
 * 
 * 3 XML pull解析并未像SAX解析那样监听元素的结束，而是在开始处完成了大部分处理。这有利于提早读取XML文件，可以极大的减少解析时间，
 * 这种优化对于连接速度较漫的移动设备而言尤为重要。对于XML文档较大但只需要文档的一部分时，XML Pull解析器则是更为有效的方法。
 * 
 * @author Administrator
 * 
 */
public class XmlParseActivity extends Activity implements OnClickListener{
	
	
	private final String fileName = "river.xml";
	
	private EditText editText;
	private Dialog mLoadingDialog;
	private Button button;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_xmlparse);
		
		editText = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);

		textView = (TextView) findViewById(R.id.textView3);
		
		mLoadingDialog = new Dialog(XmlParseActivity.this, R.style.loadingProgressDialog);
		mLoadingDialog.setContentView(R.layout.loading_progress_bar);
		mLoadingDialog.setCancelable(false);
		
		//很小的文件，不耗时，可以不用异步执行。
		InputStream is = null;
		try {
			is = this.getResources().getAssets().open(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String xml ="";
		if (is != null)
			xml = convertStreamToString(is);
		editText.setText(xml);
		
	}
	
	
	/**
	 * @author Administrator
	 * 异步执行xml解析和更新UI界面
	 */
	class XMLParseTask extends AsyncTask<String, Integer, List<River>>{
		
		@Override
		protected List<River> doInBackground(String... params) {
			String xml = params[0];
			List<River> result = new ArrayList<River>();
			try {
				/** 1. Dom解析  */
				//result = DomParseXml.getRiversFromXml(XmlParseActivity.this, xml);
				/** 2. Sax解析  */
				//result = SaxParseXml.parse(XmlParseActivity.this, xml);
				/** 3. XmlPull解析  */
				result = PullParseXml.parse(XmlParseActivity.this, xml); 
			} catch (Exception e) {
				result = null;
				e.printStackTrace(); 
			}
			return result;
		}
		
		protected void onPostExecute(List<River> result) {
			mLoadingDialog.dismiss();
			super.onPostExecute(result);
			textView.setText(String.valueOf(result.size()) + "条数据：");
			textView.append("\n\n");
			int size = result.size();
			for (int i = 0; i < size; i++) {
				River river = result.get(i);
				textView.append(river.getName() + "--" + river.getLength());
				textView.append("\n\n");
			}
		}
	}
	
	public String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				//sb.append(line + "/n");
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			XMLParseTask parseTask  = new XMLParseTask();
			mLoadingDialog.show();
			parseTask.execute(fileName);
			break;

		default:
			break;
		}
		
	}

}
