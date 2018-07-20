package com.example.demos.fragment.dynamical;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.example.demos.R;
import com.example.demos.fragment.statical.ContentFragment;
import com.example.demos.fragment.statical.ContentFragment2;

public class DynamicalActivity extends Activity implements OnClickListener {
	private RadioButton mTab1;
	private RadioButton mTab2;

	private ContentFragment contentFragment;
	private ContentFragment2 contentFragment2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dynamic_fragment_main);

		// 初始化控件和声明事件
		mTab1 = (RadioButton) findViewById(R.id.radio_button0);
		mTab2 = (RadioButton) findViewById(R.id.radio_button1);
		mTab1.setOnClickListener(this);
		mTab2.setOnClickListener(this);
		
		// 设置默认的Fragment
		setDefaultFragment();
	}

	private void setDefaultFragment() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		contentFragment = new ContentFragment();
		
		//传参数到Fragment
		Bundle bundle = new Bundle();
		bundle.putString(contentFragment.ARGUMENT_USERNANE, "test001");
		contentFragment.setArguments(bundle);
		
		transaction.replace(R.id.id_content, contentFragment);
		transaction.commit();
	}

	@Override
	public void onClick(View v) {
		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();

		switch (v.getId()) {
		case R.id.radio_button0:
			if (contentFragment == null) {
				contentFragment = new ContentFragment();
			}
			// 使用当前Fragment的布局替代id_content的控件
			transaction.replace(R.id.id_content, contentFragment);
			break;
		case R.id.radio_button1:
			if (contentFragment2 == null) {
				contentFragment2 = new ContentFragment2();
			}
			transaction.replace(R.id.id_content, contentFragment2);
			break;
		}
		//添加一个Fragment事务到回退栈：
		//transaction.addToBackStack(null);//可不加这句看看效果
		// 事务提交
		transaction.commit();
	}

}
