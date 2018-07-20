package com.example.demos.slidingmenu.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.demos.R;
import com.example.demos.slidingmenu.view.SwipeMenu;
import com.example.demos.slidingmenu.view.SwipeMenuCreator;
import com.example.demos.slidingmenu.view.SwipeMenuItem;
import com.example.demos.slidingmenu.view.SwipeMenuListView;
import com.example.demos.slidingmenu.view.SwipeMenuListView.OnMenuItemClickListener;
import com.example.demos.slidingmenu.view.SwipeMenuListView.OpenOrCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class DeletableListActivity extends SlidingFragmentActivity{

	private SlidingMenu menu;
	private Handler handler=new Handler();
	private Runnable myRunnable;
	private SwipeMenuListView listView;
	private ArrayAdapter<String> adapter;
	private List<String> dataList = new ArrayList<String>();
	private String[] items = { "0.15", "0.19", "0.17" };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidingmenu_layout_content);
		menu = getSlidingMenu();
		listView = (SwipeMenuListView) findViewById(R.id.listView);
		setBehindContentView(new View(this));
		getSlidingMenu().setSlidingEnabled(false);
		
		for (String data : items) {
			dataList.add(data);
		}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(DeletableListActivity.this);
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
				// set item width
				openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("Show");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(DeletableListActivity.this);
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		listView.setMenuCreator(creator);
		
		// step 2. listener item click event
		listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					String item = adapter.getItem(position);
					Toast.makeText(DeletableListActivity.this, item, 0).show();
					break;
				case 1:
					dataList.remove(position);
					adapter.notifyDataSetChanged();
					break;
				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(DeletableListActivity.this, position + " long click", 0).show();
				return false;
			}
		});

		listView.setOnOpenOrCloseListener(new OpenOrCloseListener() {
			@Override
			public void isOpen(boolean isOpen) {
				System.out.println(listView.getSelectedItemPosition() +"  ---  "+ listView.getSelectedItemId());
				if (!isOpen) {
					menu.setMode(SlidingMenu.LEFT);
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					handler.removeCallbacks(myRunnable);
				} else {
					menu.setMode(SlidingMenu.LEFT_RIGHT);
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					resetMenu();
				}
			}
		});
	}
	
	private void resetMenu() {
		handler.postDelayed(myRunnable = new Runnable() {
			@Override
			public void run() {
				menu.setMode(SlidingMenu.LEFT);
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			}
		}, 300);
	}
	
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}
	
}
