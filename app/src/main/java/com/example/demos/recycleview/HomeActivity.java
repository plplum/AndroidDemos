package com.example.demos.recycleview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.demos.R;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends Activity
{

	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private HomeAdapter mAdapter;

	private SwipeRefreshLayout swipeRefreshLayout;

	private int lastVisibleItem;

	private LinearLayoutManager linearLayoutManager;

	private boolean isPullToRefresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_recyclerview);

		initData();

		mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
		mAdapter = new HomeAdapter(this, mDatas);

		//mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));


		linearLayoutManager=new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);

		mRecyclerView.setLayoutManager(linearLayoutManager);

		//mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		//mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

		mRecyclerView.setAdapter(mAdapter);

		mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
		// 设置item动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		initEvent();

		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.demo_swiperefreshlayout);

		//设置刷新时动画的颜色，可以设置4个
		swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
				android.R.color.holo_red_light, android.R.color.holo_orange_light,
				android.R.color.holo_green_light);
		/*swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
						.getDisplayMetrics()));*/

		final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				//执行查询操作， 数据返回后更新adapter，停止刷新。

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mAdapter.addData(0);
						mAdapter.initFlagArrays();
						swipeRefreshLayout.setRefreshing(false);
						mAdapter.notifyDataSetChanged();
						isPullToRefresh = true;
						//mRecyclerView.scrollToPosition(0);
					}
				}, 2000);
			}
		};


		swipeRefreshLayout.setOnRefreshListener(listener);


		//进入页面自动刷新
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				listener.onRefresh();

			}
		});

		//RecyclerView滑动监听
		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
					mAdapter.changeMoreStatus(HomeAdapter.LOADING_MORE);
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							List<String> newDatas = new ArrayList<String>();
							for (int i = 0; i < 5; i++) {
								int index = i + 1;
								mDatas.add("more item" + index);
							}
							mAdapter.initFlagArrays();
							//mAdapter.notifyDataSetChanged();
							mAdapter.changeMoreStatus(HomeAdapter.PULLUP_LOAD_MORE);

						}
					},2000);
				}
			}
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
			}
		});


	}

	private void initEvent()
	{
		mAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener()
		{
			@Override
			public void onItemClick(View view, int position)
			{
				//Toast.makeText(HomeActivity.this, position + " click", Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(HomeActivity.this , StaggeredGridLayoutActivity.class);
				startActivity(intent);

			}

			@Override
			public void onItemLongClick(View view, int position)
			{
				Toast.makeText(HomeActivity.this, position + " long click",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	protected void initData()
	{
		mDatas = new ArrayList<String>();
		/*for (int i = 'A'; i < 'z'; i++)
		{
			mDatas.add("" + (char) i);
		}*/
		for (int i = 'A'; i < 'D'; i++)
		{
			mDatas.add("" + (char) i);
		}

	}


}
