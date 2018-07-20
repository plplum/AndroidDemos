package com.example.demos.slidingmenu.fragment;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;
import com.example.demos.slidingmenu.view.SwipeMenu;
import com.example.demos.slidingmenu.view.SwipeMenuCreator;
import com.example.demos.slidingmenu.view.SwipeMenuItem;
import com.example.demos.slidingmenu.view.SwipeMenuListView;
import com.example.demos.slidingmenu.view.SwipeMenuListView.OnMenuItemClickListener;
import com.example.demos.slidingmenu.view.SwipeMenuListView.OpenOrCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class ContentFragment extends Fragment {
	private View view;
	private SlidingMenu menu;
	private Context mContext;
	private List<ApplicationInfo> mAppList;
	private Handler handler=new Handler();
	private Runnable myRunnable;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.slidingmenu_layout_content, null);
		mContext=getActivity();
		setViewUp(view);
		return view;
	}

	private void setViewUp(View view) {
		mAppList = mContext.getPackageManager().getInstalledApplications(0);
		SwipeMenuListView listView = (SwipeMenuListView)view.findViewById(R.id.listView);
		AppAdapter adapter = new AppAdapter();
		listView.setAdapter(adapter);

		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						mContext.getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("Open");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						mContext.getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
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
				ApplicationInfo item = mAppList.get(position);
				switch (index) {
				case 0:
					// open
					open(item);
					break;
				case 1:
					// delete
					delete(item);
					break;
				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(mContext, position + " long click", 0).show();
				return false;
			}
		});	

		listView.setOnOpenOrCloseListener(new OpenOrCloseListener() {

			@Override
			public void isOpen(boolean isOpen) {
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
		handler.postDelayed(
				myRunnable=new Runnable() {
					@Override
					public void run() {
						menu.setMode(SlidingMenu.LEFT);
						menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);	
					}
				}, 300);
	}
	public ContentFragment(SlidingMenu menu) {
		super();
		this.menu=menu;
	}

	private void delete(ApplicationInfo item) {
		// delete app
		try {
			Intent intent = new Intent(Intent.ACTION_DELETE);
			intent.setData(Uri.fromParts("package", item.packageName, null));
			startActivity(intent);
		} catch (Exception e) {
		}
	}

	private void open(ApplicationInfo item) {
		// open app
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(item.packageName);
		List<ResolveInfo> resolveInfoList = mContext.getPackageManager()
				.queryIntentActivities(resolveIntent, 0);
		if (resolveInfoList != null && resolveInfoList.size() > 0) {
			ResolveInfo resolveInfo = resolveInfoList.get(0);
			String activityPackageName = resolveInfo.activityInfo.packageName;
			String className = resolveInfo.activityInfo.name;

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ComponentName componentName = new ComponentName(
					activityPackageName, className);

			intent.setComponent(componentName);
			startActivity(intent);
		}
	}

	class AppAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mAppList.size();
		}

		@Override
		public ApplicationInfo getItem(int position) {
			return mAppList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mContext.getApplicationContext(),
						R.layout.slidingmenu_item_list_app, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ApplicationInfo item = getItem(position);
			holder.iv_icon.setImageDrawable(item.loadIcon(mContext.getPackageManager()));
			holder.tv_name.setText(item.loadLabel(mContext.getPackageManager()));
			return convertView;
		}

		class ViewHolder {
			ImageView iv_icon;
			TextView tv_name;

			public ViewHolder(View view) {
				iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				tv_name = (TextView) view.findViewById(R.id.tv_name);
				view.setTag(this);
			}
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}


}
