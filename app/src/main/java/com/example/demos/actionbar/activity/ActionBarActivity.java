package com.example.demos.actionbar.activity;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;
import com.example.demos.actionbar.adapter.QuerySuggestionsAdapter;

public class ActionBarActivity extends FragmentActivity {
	
	private Cursor cursor; 
	private QuerySuggestionsAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actionbar_main);
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActionBarActivity.this, SearchViewActivity.class);
				startActivity(intent);
			}
		});
		
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActionBarActivity.this, CustomActionBarActivity.class);
				startActivity(intent);
			}
		});

		ActionBar actionBar = getActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(false);
		//actionBar.setDisplayShowHomeEnabled(true);
		//actionBar.setDisplayShowTitleEnabled(false);
		//actionBar.setHomeButtonEnabled(true);
		//actionBar.setLogo(R.drawable.ic_list_white_48dp);
		//actionBar.setDisplayUseLogoEnabled(true);
		
		//返回按钮
		actionBar.setTitle("返回");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		
		//String strCenterTitle = getResources().getString(R.string.app_name);
		String strCenterTitle = "ActionBar";
		initTitleCenterActionBar(ActionBarActivity.this, actionBar, strCenterTitle);
		
		
	
		
	}
	
	
	/**
	 * 设置居中标题
	 * @param mContext 上下文
	 * @param actionBar actionbar
	 * @param strCenterTitle 中间居中显示标题
	 */
	public static void initTitleCenterActionBar(Context mContext,ActionBar actionBar,String strCenterTitle){
		LayoutInflater inflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View centerTitle = inflator.inflate(R.layout.view_actionbar_title, null);
		TextView title = (TextView) centerTitle.findViewById(R.id.actionbar_title);
        title.setText(strCenterTitle);
		ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
		
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(centerTitle,layoutParams);
		
		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	this.finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_main, menu);
		
		
		MenuItem searchItem = menu.findItem(R.id.action_search);
		final SearchView searchView = (SearchView) searchItem.getActionView();
		//searchView.setIconifiedByDefault(false);
		//searchView.setSubmitButtonEnabled(true);
		searchView.setQueryHint("查询");
		cursor = getTestCursor("");
 		/*while(cursor.moveToNext()){
 			System.out.println(cursor.getString(1));
 		}*/
		adapter = new QuerySuggestionsAdapter(ActionBarActivity.this, cursor) ;
		searchView.setSuggestionsAdapter(adapter);
		//new SimpleCursorAdapter(this, R.layout.search_item_layout, cursor, new String[] { "tb_name" }, new int[] { R.id.textview });

		searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String str) {
            	System.out.println("str  = " + str);
            	cursor = getTestCursor(str);
            	while(cursor.moveToNext()){
            		System.out.println(cursor.getString(1));
            	}
            	adapter.changeCursor(cursor);
            	//adapter.notifyDataSetChanged();       	
                return false;
            }
 
            @Override
            public boolean onQueryTextSubmit(String str) {
                Toast.makeText(ActionBarActivity.this, str, Toast.LENGTH_SHORT).show();
                return false;
            }
 
        });
 
		return super.onCreateOptionsMenu(menu);
	}

	// 添加suggestion需要的数据
	public Cursor getTestCursor(String str) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir() + "/test.db", null);
		Cursor cursor = null;
		try {
			String delete = "delete from tb_test";
			db.execSQL(delete);
			
			String insertSql = "insert into tb_test values (null,?,?)";
			db.execSQL(insertSql, new Object[] { "aabc", 1 });
			db.execSQL(insertSql, new Object[] { "aabd", 2 });
			db.execSQL(insertSql, new Object[] { "ddef", 3 });
			db.execSQL(insertSql, new Object[] { "ffeg", 4 });
			db.execSQL(insertSql, new Object[] { "bbhj", 5 });

			String querySql = "select * from tb_test where tb_name like '%"+ str +"%'";
			cursor = db.rawQuery(querySql, null);
		} catch (Exception e) {
			String sql = "create table tb_test (_id integer primary key autoincrement,tb_name varchar(20),tb_age integer)";
			db.execSQL(sql);
			String insertSql = "insert into tb_test values (null,?,?)";
			db.execSQL(insertSql, new Object[] { "aabc", 1 });
			db.execSQL(insertSql, new Object[] { "aabd", 2 });
			db.execSQL(insertSql, new Object[] { "ddef", 3 });
			db.execSQL(insertSql, new Object[] { "ffeg", 4 });
			db.execSQL(insertSql, new Object[] { "bbhj", 5 });
			
			String querySql = "select * from tb_test where tb_name like '%"+ str +"%'";
			cursor = db.rawQuery(querySql, null);
		}

		return cursor;
	}

}
