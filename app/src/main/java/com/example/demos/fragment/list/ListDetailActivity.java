package com.example.demos.fragment.list;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.example.demos.R;

public class ListDetailActivity extends Activity
{

	private ListDetailFragment mContentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_fragment);
	
		FragmentManager fm = getFragmentManager();
		mContentFragment = (ListDetailFragment) fm.findFragmentById(R.id.id_fragment_container);
		
		if(mContentFragment == null )
		{
			String title = getIntent().getStringExtra(ListDetailFragment.ARGUMENT);
			mContentFragment = ListDetailFragment.newInstance(title);
			fm.beginTransaction().add(R.id.id_fragment_container,mContentFragment).commit();
		}

	}
}