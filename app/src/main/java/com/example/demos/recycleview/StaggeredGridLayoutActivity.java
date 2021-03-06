package com.example.demos.recycleview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.demos.R;

import java.util.ArrayList;
import java.util.List;

public class StaggeredGridLayoutActivity extends Activity
{

	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private StaggeredHomeAdapter mStaggeredHomeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_recyclerview);

		initData();

		mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
		mStaggeredHomeAdapter = new StaggeredHomeAdapter(this, mDatas);

		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
				StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.setAdapter(mStaggeredHomeAdapter);
		// 设置item动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		initEvent();

	}

	private void initEvent()
	{
		mStaggeredHomeAdapter.setOnItemClickLitener(new StaggeredHomeAdapter.OnItemClickLitener()
		{
			@Override
			public void onItemClick(View view, int position)
			{
				Toast.makeText(StaggeredGridLayoutActivity.this,
						position + " click", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onItemLongClick(View view, int position)
			{
				Toast.makeText(StaggeredGridLayoutActivity.this,
						position + " long click", Toast.LENGTH_SHORT).show();
			}
		});
	}

	protected void initData()
	{
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++)
		{
			mDatas.add("" + (char) i);
		}
	}

}
