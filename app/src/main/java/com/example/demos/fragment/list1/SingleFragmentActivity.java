package com.example.demos.fragment.list1;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.example.demos.R;

public abstract class SingleFragmentActivity extends Activity
{
	protected abstract Fragment createFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_fragment);
	
		FragmentManager fm = getFragmentManager();
		Fragment fragment =fm.findFragmentById(R.id.id_fragment_container);
		
		if(fragment == null )
		{
			fragment = createFragment() ;
			
			fm.beginTransaction().add(R.id.id_fragment_container,fragment).commit();
		}
	}
	
}
