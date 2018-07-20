package com.example.demos.actionbar.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.example.demos.R;

public class GroupChatActivity extends FragmentActivity {
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		TextView textView = new TextView(this);
		textView.setText("This is grpup chat view.");
		setContentView(textView);

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	

}
