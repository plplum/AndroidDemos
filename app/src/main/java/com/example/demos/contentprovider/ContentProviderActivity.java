package com.example.demos.contentprovider;

import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.demos.R;

/**
 * 这个类用来测试ContentProvider,通过 给定的uri访问数据库.
 * 
 */
public class ContentProviderActivity extends Activity {
	Button insert;
	Button query;
	Button update;
	Button delete;
	Button querys;
	TextView textView;
	
	//老师列表URI
	Uri uri = Uri.parse("content://test.android.contentProvider/teacher");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contentprovider);

		textView = (TextView) findViewById(R.id.info);
		
		insert = (Button) findViewById(R.id.insert);
		query = (Button) findViewById(R.id.query);
		update = (Button) findViewById(R.id.update);
		delete = (Button) findViewById(R.id.delete);
		querys = (Button) findViewById(R.id.querys);
		// 绑定监听器的两种方法一；
		insert.setOnClickListener(new InsertListener());
		query.setOnClickListener(new QueryListener());
		// 方法二
		update.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentResolver cr = getContentResolver();
				ContentValues cv = new ContentValues();
				cv.put("name", "huangbiao");
				cv.put("date_added", (new Date()).toString());
				int uri2 = cr.update(uri, cv, "_ID=?", new String[] { "3" });
				textView.setText("更新成功，row = " + uri2);
			}
		});

		delete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ContentResolver cr = getContentResolver();
				int num = cr.delete(uri, "_ID=?", new String[] { "2" });
				textView.setText("删除成功，row = " + num);
			}
		});

		querys.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentResolver cr = getContentResolver();
				// 查找id为1的数据
				Cursor c = cr.query(uri, null, null, null, null);
				c.close();
				textView.setText("查询结果，count = " + c.getCount());
			}
		});
	}

	class InsertListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			ContentResolver cr = getContentResolver();

			ContentValues cv = new ContentValues();
			cv.put("title", "jiaoshou");
			cv.put("name", "jiaoshi");
			cv.put("sex", true);
			Uri uri2 = cr.insert(uri, cv);
			textView.setText("插入成功，row = " + uri2.toString());
		}

	}

	class QueryListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			ContentResolver cr = getContentResolver();
			// 查找id为1的数据: 两种写法 1、Cursor c = cr.query(uri, null, "_ID=?", new String[] { "1" }, null);
			//2.Uri uri = Uri.parse("content://test.android.contentProvider/teacher/1"); Cursor c = cr.query(uri, null, null, null, null);
			//Cursor c = cr.query(uri, null, "_ID=?", new String[] { "1" }, null);
			Uri uri = Uri.parse("content://test.android.contentProvider/teacher/1");
			Cursor c = cr.query(uri, null, null, null, null);
			// 这里必须要调用 c.moveToFirst将游标移动到第一条数据,不然会出现index -1 requested , with a
			// size of 1错误；cr.query返回的是一个结果集。
			if (c.moveToFirst() == false) {
				// 为空的Cursor
				return;
			}
			int name = c.getColumnIndex("name");
			textView.setText("查询结果，row = 1 ，name = " + c.getString(name));
			c.close();
		}
	}
}