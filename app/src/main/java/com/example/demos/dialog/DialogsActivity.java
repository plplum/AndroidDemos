package com.example.demos.dialog;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.demos.R;

public class DialogsActivity extends Activity {

	public GridView gridView;
	
	String[] items = {"确认Dialog", "三个按钮Dialog", "输入Dialog" ,"单选列表Dialog", "多选列表Dialog", "列表Dialog", "自定义Dialog布局", "进度对话框"
			, "PopUp Dialog 背景透明", "自定义Dialog"} ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialogs_activity_main);

		gridView = (GridView) findViewById(R.id.dialogs_gridview);

		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < items.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.ic_launcher);
			map.put("ItemText", items[i]);
			lstImageItem.add(map);
		}
		
		SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,
				R.layout.dialog_gridview_item,
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.item_image, R.id.item_text });
		
		gridView.setAdapter(saImageItems);
	
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					dialog1();
					break;
				case 1:
					dialog2();
					break;
				case 2:
					dialog3();
					break;
				case 3:
					dialog4();
					break;
				case 4:
					dialog5();
					break;
				case 5:
					dialog6();
					break;
				case 6:
					dialog7();
					break;
				case 7:
					dialog8();
					//showProcessDialog();
					break;
				case 8:
					showPopUpDialog(arg1);
					break;
				case 9:
					showCustomDialog(arg1);
					break;
				default:
					break;
				}
			}
		});
	}

	protected void dialog1() {
		AlertDialog.Builder builder = new Builder(DialogsActivity.this);
		builder.setMessage("确认退出吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//TODO put your code here 
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	protected void dialog2() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.btn_star).setTitle("喜好调查")
				.setMessage("你喜欢李连杰的电影吗？")
				.setPositiveButton("很喜欢", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogsActivity.this, "我很喜欢他的电影。",
								Toast.LENGTH_LONG).show();
					}
				}).setNegativeButton("不喜欢", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogsActivity.this, "我不喜欢他的电影。",
								Toast.LENGTH_LONG).show();
					}
				}).setNeutralButton("一般", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogsActivity.this, "谈不上喜欢不喜欢。",
								Toast.LENGTH_LONG).show();
					}
				}).create();
		dialog.show();
	}
	
	
	protected void dialog3() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入")
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogsActivity.this, editText.getText().toString(),
								Toast.LENGTH_LONG).show();
					}
				}).setNegativeButton("取消", null).show();

	}
	
	
	
	protected void dialog4() {
		new AlertDialog.Builder(this)
				.setTitle("单选框")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(new String[] { "Item1", "Item2" }, 0,
						new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).setNegativeButton("取消", null).show();
	}
		
	protected void dialog5() {
		new AlertDialog.Builder(this)
				.setTitle("复选框")
				.setMultiChoiceItems(new String[] { "Item1", "Item2" }, null, null)
				.setPositiveButton("确定", null)
				.setNegativeButton("取消", null).show();
	}
	
	protected void dialog6() {
		new AlertDialog.Builder(this).setTitle("列表框")
				.setItems(new String[] { "Item1", "Item2" }, null)
				.setNegativeButton("确定", null).show();
	}
	
	protected void dialog7() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.dialog_custom, (ViewGroup) findViewById(R.id.dialog));
		new AlertDialog.Builder(this).setTitle("自定义布局").setView(layout)
				.setPositiveButton("确定", null).setNegativeButton("取消", null)
				.show();
	}
	
	 //模拟进度条对话框  
    ProgressDialog processDialog=null;  
    public final static int MAX_READPROCESS = 100;  
    private void dialog8()  
    {  
        processDialog=new ProgressDialog(DialogsActivity.this);  
        processDialog.setProgress(0);  
        processDialog.setTitle("进度条窗口");  
        processDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
        processDialog.setMax(MAX_READPROCESS);  
        processDialog.show();  
        
      //新开启一个线程，循环的累加，一直到100然后在停止  
        new Thread(){
        	public void run() {
        		  
                int Progress= 0;  
                while(Progress < MAX_READPROCESS)  
                {  
                    try {  
                        Thread.sleep(100);  
                        Progress++;  
                        processDialog.incrementProgressBy(1);  
                    } catch (InterruptedException e) {  
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                    }  
                }  
                //读取完了以后窗口自消失  
                processDialog.cancel();  
            
        	};
        }.start();  
    }  
      
    private void showProcessDialog()  
    {  
        ProgressDialog processDialog= new ProgressDialog(DialogsActivity.this);  
        processDialog.setTitle("进度条框");  
        processDialog.setMessage("内容读取中...");  
        processDialog.setIndeterminate(true);  
        processDialog.setCancelable(true);  
        processDialog.show();  
    }
	
    
    private PopupWindow window=null;  
    private View popupView;  

	/** popup window 来实现 */
	private void showPopUpDialog(View parent) {
		if (window == null) {
			popupView = LayoutInflater.from(DialogsActivity.this).inflate(R.layout.dialog_normal_layout1, null);
			
			//EditText editText = (EditText) popupView.findViewById(R.id.dialog_edittext_name);
			
			window = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}
		/*
		 * 必须调用setBackgroundDrawable，
		 * 因为popupwindow在初始时，会检测background是否为null,如果是onTouch or onKey
		 * events就不会相应，所以必须设置background
		 */
		/* 网上也有很多人说，弹出pop之后，不响应键盘事件了，这个其实是焦点在pop里面的view去了。 */
		window.setFocusable(true);
		window.setBackgroundDrawable(new ColorDrawable(0));
		window.update();
		window.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);

	}
      
	
	public void showCustomDialog(View view) {
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setMessage("这个就是自定义的提示框");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//设置你的操作事项
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}
	
}

