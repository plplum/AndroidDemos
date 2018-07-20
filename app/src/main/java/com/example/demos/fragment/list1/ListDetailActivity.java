package com.example.demos.fragment.list1;

import android.app.Fragment;

import com.example.demos.fragment.list.ListDetailFragment;


public class ListDetailActivity extends SingleFragmentActivity
{
	private ListDetailFragment mContentFragment;

	@Override
	protected Fragment createFragment()
	{
		String title = getIntent().getStringExtra(ListDetailFragment.ARGUMENT);

		mContentFragment = ListDetailFragment.newInstance(title);
		return mContentFragment;
	}
}
