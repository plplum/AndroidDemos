package com.example.demos.fragment.list1;

import android.app.Fragment;

import com.example.demos.fragment.list.ListTitleFragment;


public class ListTitleActivity extends SingleFragmentActivity
{
	private ListTitleFragment mListFragment;

	@Override
	protected Fragment createFragment()
	{
		mListFragment = new ListTitleFragment();
		return mListFragment;
	}
}
