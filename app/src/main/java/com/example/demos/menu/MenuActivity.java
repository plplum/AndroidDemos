package com.example.demos.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;

public class MenuActivity extends Activity implements OnClickListener{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_main);
		
		TextView textView = (TextView) findViewById(R.id.textView1);
		//给View 的子控件绑定ContextMenu 菜单，长按此子控件就会弹出ContextMenu菜单  
		registerForContextMenu(textView);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		//创建菜单
		inflater.inflate(R.menu.menu, menu);
		//创建子菜单
		//inflater.inflate(R.menu.submenu, menu);
		return true;
		// return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about: {
			Toast.makeText(this, "menu_about event", Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.menu_advise: {
			Toast.makeText(this, "menu_advise event", Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.menu_exit: {
			Toast.makeText(this, "menu_exit event", Toast.LENGTH_SHORT).show();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about: {
			Toast.makeText(this, "menu_about event", Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.menu_advise: {
			Toast.makeText(this, "menu_advise event", Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.menu_exit: {
			Toast.makeText(this, "menu_exit event", Toast.LENGTH_SHORT).show();
			break;
		}
		}
		return super.onContextItemSelected(item);
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent intent = new Intent(MenuActivity.this, CustomMenuActivity.class);
			startActivity(intent);
			break;
		case R.id.button2:
			intent = new Intent(MenuActivity.this, TabHostMenuActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}
	
}
