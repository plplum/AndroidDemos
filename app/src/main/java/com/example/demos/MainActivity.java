package com.example.demos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demos.actionbar.activity.ActionBarActivity;
import com.example.demos.animations.AnimationsActivity;
import com.example.demos.autoplay.activity.AutoPlayActivity;
import com.example.demos.bottomnavigation.MainActivity5;
import com.example.demos.broadcast.BroadCastActivity;
import com.example.demos.changetheme.MainActivity6;
import com.example.demos.city.activity.SelectCtiyActivity;
import com.example.demos.citylist.CityListActivity;
import com.example.demos.contacts.activity.ContactsActivity;
import com.example.demos.contentprovider.ContentProviderActivity;
import com.example.demos.dialog.DialogsActivity;
import com.example.demos.drawerlayout.DrawerLayoutActivity;
import com.example.demos.dynamicloading.activity.RuningManActivity;
import com.example.demos.fragment.dialog.DialogActivity;
import com.example.demos.fragment.dynamical.DynamicalActivity;
import com.example.demos.fragment.list.ListTitleActivity;
import com.example.demos.fragment.statical.StaticalActivity;
import com.example.demos.guidepage.activity.GuideActivity;
import com.example.demos.headicon.activity.HeadIconActivity;
import com.example.demos.http.HttpMainActivity;
import com.example.demos.json.JsonParseActivity;
import com.example.demos.language.ChangeLanguageActivity;
import com.example.demos.loadimages.LoadImagesActivity;
import com.example.demos.menu.MenuActivity;
import com.example.demos.nestedscrollview.activity.Main4Activity;
import com.example.demos.okhttp3.activity.Main3Activity;
import com.example.demos.popupwindow.PopupWindowActivity;
import com.example.demos.progresswebview.activity.WebActivity;
import com.example.demos.pulltorefresh.PullToRefreshActivity;
import com.example.demos.recycleview.HomeActivity;
import com.example.demos.refreshview.activity.RefreshableListViewActivity;
import com.example.demos.refreshview.activity.RefreshableScrollViewActivity;
import com.example.demos.removablebar.ShowHideSearchBarActivity1;
import com.example.demos.retrofit.Main2Activity;
import com.example.demos.service.onbind.ServiceOnBindActivity;
import com.example.demos.service.onstart.ServiceOnStartActivity;
import com.example.demos.slidable.SlidableActivity;
import com.example.demos.slidingmenu.activity.DeletableListActivity;
import com.example.demos.slidingmenu.activity.SlidingMenuActivity;
import com.example.demos.slidingmenu.activity.SlidingMenuMainActivity;
import com.example.demos.socket.SocketClientActivity;
import com.example.demos.statusbar.StatusBarActivity;
import com.example.demos.theme.ChangeThemeActivity;
import com.example.demos.util.LogUtil;
import com.example.demos.volleydemo.VolleyMainActivity;
import com.example.demos.webservice.WebServiceActivity;
import com.example.demos.xmlparse.XmlParseActivity;

public class MainActivity extends Activity implements OnItemClickListener, DialogInterface.OnClickListener{
	
	public static final String TAG = "MainActivity";
	
	
	private ListView demosListView;
	
	private DemosAdapter demosAdapter;
	
	private static String DIALOG_FLAG;
	
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	private static int THEME_DARK = 0;
	private static int THEME_LIGHT = 1;
	
	public int mTheme = R.style.Theme_Light;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.i(TAG, "onCreat");
		//更改主题
		/*sharedPreferences = this.getSharedPreferences("androidDemos", 0);
		editor = sharedPreferences.edit();
		
		mTheme = sharedPreferences.getInt("androidDemos_theme", 0);
		if(mTheme == THEME_DARK){
			setTheme(R.style.Theme_Dark);
		}else {
			setTheme(R.style.Theme_Light);
		}*/
		
		setContentView(R.layout.activity_main);
		
		demosListView = (ListView) this.findViewById(R.id.demoes_list);
		
		demosAdapter = new DemosAdapter(this, R.layout.list_item_demo);
		
		int iconResourceId = R.drawable.main_menu_btn_ic_star_hl;
		
		
		String[] names = getResources().getStringArray(R.array.name);
		String[] descriptions = getResources().getStringArray(R.array.description);
		
		int size = names.length;
		
		for (int i = 0; i < size; i++) {
			demosAdapter.add(new DemoListItem(iconResourceId, names[i], descriptions[i]));
		}
		
		demosListView.setAdapter(demosAdapter);
		
		demosListView.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		LogUtil.i(TAG, "onItemClick : " + arg2);
		Intent intent = null;
		switch (arg2) {
		case 0:
			intent = new Intent(MainActivity.this, SplashscreenActivity.class);
			break;
		case 1:
			intent = new Intent(MainActivity.this, GuideActivity.class);
			break;
		case 2:
			intent = new Intent(MainActivity.this, ContactsActivity.class);
			break;
		case 3:
			intent = new Intent(MainActivity.this, SelectCtiyActivity.class);
			break;
		case 4:
			intent = new Intent(MainActivity.this, PullToRefreshActivity.class);
			break;
		case 5:
			DIALOG_FLAG = "RefreshView";
			String[] items = new String[] {"ListView", "ScrollView" };
			Dialog dialog = this.bulidOptionsList(this, "演示列表", items, this);
			dialog.show();
			break;
		case 6:
			intent = new Intent(MainActivity.this, RuningManActivity.class);
			break;
		case 7:
			intent = new Intent(MainActivity.this, SlidingMenuMainActivity.class);
			break;
		case 8:
			intent = new Intent(MainActivity.this, SlidingMenuActivity.class);
			break;
		case 9:
			intent = new Intent(MainActivity.this, DeletableListActivity.class);
			break;
		case 10:
			intent = new Intent(MainActivity.this, DialogsActivity.class);
			break;
		case 11:
			intent = new Intent(MainActivity.this, PopupWindowActivity.class);
			break;
		case 12:
			intent = new Intent(MainActivity.this, MenuActivity.class);
			break;
		case 13:
			intent = new Intent(MainActivity.this, ActionBarActivity.class);
			break;
		case 14:
			intent = new Intent(MainActivity.this, AutoPlayActivity.class);
			break;
		case 15:
			intent = new Intent(MainActivity.this, com.example.demos.baidumap.activity.MainActivity.class);
			break;
		case 16:
			//intent = new Intent(MainActivity.this, StaticalActivity.class);
			DIALOG_FLAG = "Fragment";
			String[] fragmentItems = new String[] {"静态使用Fragment", "动态使用Fragment", "ListFragment和参数传递" , "DailogFragment和参数传递"};
			Dialog fragmentDialog = this.bulidOptionsList(this, "演示列表", fragmentItems, this);
			fragmentDialog.setTitle("Dialog_Fragment");
			fragmentDialog.show();
			break;
		case 17:
			intent = new Intent(MainActivity.this, ChangeThemeActivity.class);
			break;
		case 18:
			intent = new Intent(MainActivity.this, ChangeLanguageActivity.class);
			break;
		case 19:
			intent = new Intent(MainActivity.this, HttpMainActivity.class);
			break;
		case 20:
			intent = new Intent(MainActivity.this, SocketClientActivity.class);
			break;
		case 21:
			intent = new Intent(MainActivity.this, BroadCastActivity.class);
			break;
		case 22:
			DIALOG_FLAG = "Service";
			items = new String[] {"OnStart", "OnBind" };
			dialog = this.bulidOptionsList(this, "演示列表", items, this);
			dialog.show();
			break;
		case 23:
			intent = new Intent(MainActivity.this, com.example.demos.notifications.MainActivity.class);
			break;
		case 24:
			intent = new Intent(MainActivity.this, ContentProviderActivity.class);
			break;
		case 25:
			intent = new Intent(MainActivity.this, com.example.demos.horizontalmenu.activity.MainActivity.class);
			break;
		case 26:
			intent = new Intent(MainActivity.this,  com.example.demos.navigationmenu.MainActivity.class);
			break;
		case 27:
			intent = new Intent(MainActivity.this,  XmlParseActivity.class);
			break;
		case 28:
			intent = new Intent(MainActivity.this,  JsonParseActivity.class);
			break;
		case 29:
			intent = new Intent(MainActivity.this,  CityListActivity.class);
			break;
		case 30:
			intent = new Intent(MainActivity.this,  com.example.demos.slidebottommenu.activity.MainActivity.class);
			break;
		case 31:
			intent = new Intent(MainActivity.this,  com.example.demos.fragmentmenu.MainActivity.class);
			break;
		case 32:
			intent = new Intent(MainActivity.this,  com.example.demos.categorymenu.MainActivity.class);
			break;
		case 33:
			//intent = new Intent(MainActivity.this,  ShowHideSearchBarActivity.class);
			intent = new Intent(MainActivity.this,  ShowHideSearchBarActivity1.class);
			break;
		case 34:
			intent = new Intent(MainActivity.this,  LoadImagesActivity.class);
			break;
		case 35:
			intent = new Intent(MainActivity.this,  WebActivity.class);
			intent.putExtra("url", "http://www.baidu.com");
			intent.putExtra("name", "百度");
			break;
		case 36:
			intent = new Intent(MainActivity.this,  HeadIconActivity.class);
			break;
		case 37:
			intent = new Intent(MainActivity.this,  com.example.demos.province.MainActivity.class);
			break;
		case 38:
			intent = new Intent(MainActivity.this,  VolleyMainActivity.class);
			break;
		case 39:
			intent = new Intent(MainActivity.this,  DrawerLayoutActivity.class);
			break;
		case 40:
			intent = new Intent(MainActivity.this,  AnimationsActivity.class);
			break;
		case 41:
			intent = new Intent(MainActivity.this,  WebServiceActivity.class);
			break;
		case 42:
			//intent = new Intent(MainActivity.this,  com.example.demos.sharephoto.MainActivity.class);
			intent = new Intent(MainActivity.this,  com.example.demos.sharephotonew.MainActivity.class);
			break;
		case 43:
			intent = new Intent(MainActivity.this,  com.example.demos.gridview.MainActivity.class);
			break;
		case 44:
			intent = new Intent(MainActivity.this, HomeActivity.class);
			break;
		case 45:
			intent = new Intent(MainActivity.this, StatusBarActivity.class);
			break;
		case 46:
			intent = new Intent(MainActivity.this, com.example.demos.toolbar.MainActivity.class);
			break;
		case 47:
			intent = new Intent(MainActivity.this, com.example.demos.segmentedgroup.MainActivity.class);
			break;
		case 48:
			intent = new Intent(MainActivity.this, Main2Activity.class);
			break;
		case 49:
			intent = new Intent(MainActivity.this, Main3Activity.class);
			break;
		case 50:
			intent = new Intent(MainActivity.this, Main4Activity.class);
			break;
		case 51:
			intent = new Intent(MainActivity.this, MainActivity5.class);
			break;
		case 52:
			intent = new Intent(MainActivity.this, MainActivity6.class);
			break;
		case 53:
			intent = new Intent(MainActivity.this, SlidableActivity.class);
			break;
		case 54:
			intent = new Intent(MainActivity.this, com.example.demos.splashscreen.MainActivity.class);
			break;
		case 55:
			intent = new Intent(MainActivity.this, com.example.demos.toolbar1.MainActivity.class);
			break;
		default:
			break;
		}
		if(intent == null) return;
		startActivity(intent);
	}
	
	public Dialog bulidOptionsList(Context context, String title, String[] items, DialogInterface.OnClickListener listener ){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		//builder.setTitle(titleID);
		//builder.setSingleChoiceItems((CharSequence[])context.getResources().getStringArray(arrayId), selectedId, listener);
		builder.setItems(items, listener);
		builder.setNegativeButton("取消", null);
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(DIALOG_FLAG.equals("RefreshView")){
			switch (which) {
			case 0:
				Intent intent = new Intent(MainActivity.this, RefreshableListViewActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(MainActivity.this, RefreshableScrollViewActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		} else if(DIALOG_FLAG.equals("Fragment")){
			switch (which) {
			case 0:
				Intent intent = new Intent(MainActivity.this, StaticalActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(MainActivity.this, DynamicalActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(MainActivity.this, ListTitleActivity.class);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent(MainActivity.this, DialogActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		} else if(DIALOG_FLAG.equals("Service")){
			switch (which) {
			case 0:
				Intent intent = new Intent(MainActivity.this, ServiceOnStartActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(MainActivity.this, ServiceOnBindActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	}
	
	
	 protected void onResume() {
         super.onResume();
         //设置页面更改主题后，判断当前页面是否需要更改主题
         /*if (mTheme != sharedPreferences.getInt("androidDemos_theme", 0)) {
             reload();
         }*/
	 }
	
	 protected void reload() {
         Intent intent = getIntent();
         overridePendingTransition(0, 0);
         intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         finish();
         overridePendingTransition(0, 0);
         startActivity(intent);
	 }
	 
	//返回键被点击次数
	private int mBackKeyPressedTimes = 0;
	
	private boolean doubleBackToExitPressedOnce = false;
	/* 
	 * 双击返回键退出程序
	 * (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	public void onBackPressed() {
		/*if (mBackKeyPressedTimes == 0) {
			Toast.makeText(this, "再按一次退出程序 ", Toast.LENGTH_SHORT).show();
			mBackKeyPressedTimes = 1;
			new Thread() {
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						mBackKeyPressedTimes = 0;
					}
				}
			}.start();
		} else {
			super.onBackPressed();
			this.finish();
			System.exit(0);
		}
	*/
	
	 if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 2000);
	
	}

}
