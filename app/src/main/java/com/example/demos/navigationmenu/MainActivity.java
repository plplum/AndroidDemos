package com.example.demos.navigationmenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.demos.R;
import com.example.demos.navigationmenu.fragment.AnimationFragment;
import com.example.demos.navigationmenu.fragment.Fragment1;
import com.example.demos.navigationmenu.fragment.Fragment2;
import com.example.demos.navigationmenu.fragment.Fragment3;

public class MainActivity extends FragmentActivity {

	/** top barId */
	private Integer[] barsId = { R.id.btn_01, R.id.btn_02, R.id.btn_03 };

	/**
	 * top bar
	 */
	private static List<Button> listBars;

	/**
	 * store select state of bar
	 */
	public View externView;
	private ImageView mIndicator;
	private MyFragmentPagerAdapter mFragmentAdapter;
	private ViewPager mPager;
	private static final int ANIMATION_DURATION = 300;
	private static final int TOP_BTN_ONE = 0;
	private static final int TOP_BTN_TWO = 1;
	private static final int TOP_BTN_THREE = 2;
	private static final int TOP_BTN_NUMBER = 3;
	private int mLastItemPosition = TOP_BTN_ONE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationmenu);

		mPager = (ViewPager) findViewById(R.id.vPager);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		listBars = new ArrayList<Button>();
		for (Integer barId : barsId) {
			listBars.add((Button) findViewById(barId));
		}
		externView = new View(this);
		mFragmentAdapter = new MyFragmentPagerAdapter(
				this.getSupportFragmentManager(), this);
		mPager.setAdapter(mFragmentAdapter);
		mFragmentAdapter.addTab(Fragment1.class, null);
		mFragmentAdapter.addTab(Fragment2.class, null);
		mFragmentAdapter.addTab(Fragment3.class, null);
		mPager.setOffscreenPageLimit(TOP_BTN_NUMBER);
		mIndicator = (ImageView) findViewById(R.id.home_top_indicate);
		Button mImageViewOne = (Button) findViewById(R.id.btn_01);
		mImageViewOne.setSelected(true);

	}

	/**
	 * 点击按钮后选中
	 * */
	private void setTopBtnStatus(int selectedPos) {
		listBars.get(selectedPos).setSelected(true);
		mLastItemPosition = selectedPos;
		for (int i = 0; i < listBars.size() && i != selectedPos; i++) {
			listBars.get(i).setSelected(false);
		}
	}

	/**
	 * 滑动动画
	 * */
	private void startAnimation(int lastIndex, int curIndex) {
		if (lastIndex < 0 || lastIndex >= TOP_BTN_NUMBER || curIndex < 0
				|| curIndex >= TOP_BTN_NUMBER || lastIndex == curIndex) {
			return;
		}

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;

		int position_one = (int) (screenW / 3.0);
		//int fromX = position_one * (lastIndex + 1) - position_one / 2;
		int fromX = position_one * lastIndex;
		int OriginX = mIndicator.getRight() - mIndicator.getWidth() / 2;
		//int toX = position_one * (curIndex + 1) - position_one / 2;
		int toX = position_one * curIndex ;

		//TranslateAnimation transAnim = new TranslateAnimation(fromX - OriginX, toX - OriginX, 0, 0);
		TranslateAnimation transAnim = new TranslateAnimation(fromX, toX, 0, 0);
		transAnim.setFillAfter(true);
		transAnim.setDuration(ANIMATION_DURATION);
		mIndicator.startAnimation(transAnim);
	}

	private int mLastPos = -1;
	private int mPageState;
	private int mLastPageState = -1;
	private int mStartScroll = AnimationFragment.DIRECT_TYPE_INVALID;
	private int mCurScrollPage;

	private class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
			mLastPos = -1;
			mPageState = arg0;
		}

		@Override
		public void onPageScrolled(int page, float ratio, int coordinate) {

			if (mLastPos == -1 || mLastPos == 0 || coordinate == 0) {
				mLastPos = coordinate;
			} else {
				if (mLastPos - coordinate > 0) {
					if (mPageState == 1 && mLastPageState == -1) {
						//listItemScrollLocate(ratio, page,AnimationFragment.DIRECT_TYPE_RIGHT,AnimationFragment.SCROLL_START);
						//mStartScroll = AnimationFragment.DIRECT_TYPE_RIGHT;
						mCurScrollPage = page;
					} else {
						if (mStartScroll != AnimationFragment.DIRECT_TYPE_INVALID) {
							//listItemScroll(ratio, mCurScrollPage, mStartScroll,	AnimationFragment.DIRECT_TYPE_RIGHT);
						}
					}
				} else if (mLastPos - coordinate < 0) {
					if (mPageState == 1 && mLastPageState == -1) {
						//listItemScrollLocate(ratio, page + 1,AnimationFragment.DIRECT_TYPE_LEFT,AnimationFragment.SCROLL_START);
						//mStartScroll = AnimationFragment.DIRECT_TYPE_LEFT;
						mCurScrollPage = page + 1;
					} else {
						if (mStartScroll != AnimationFragment.DIRECT_TYPE_INVALID) {
							//listItemScroll(ratio, mCurScrollPage, mStartScroll,AnimationFragment.DIRECT_TYPE_LEFT);
						}
					}
				}
				mLastPos = coordinate;
				mLastPageState = mPageState;
			}
		}

		@Override
		public void onPageSelected(int arg0) {
			try {
				int curItem = arg0;
				if (mLastItemPosition != curItem) {
					if (mPageState == 0 && mLastItemPosition >= 0
							&& mLastItemPosition < TOP_BTN_NUMBER) {
						externView.post(new Runnable() {
							@Override
							public void run() {
								mPager.setCurrentItem(mLastItemPosition);
							}
						});
						return;
					} else {
						startAnimation(mLastItemPosition, curItem);
					}
				}

				if (externView.getTag() != null) {
					((View) (externView.getTag())).setSelected(false);
				} else {
					externView = new View(MainActivity.this);
				}
				if (listBars != null) {
					externView.setTag(listBars.get(arg0));
					setTopBtnStatus(arg0);
				} else {
					listBars = new ArrayList<Button>();
					for (Integer barId : barsId) {
						listBars.add((Button) findViewById(barId));
					}
				}
				switch (barsId[arg0]) {
				case R.id.btn_01:
					setTopBtnStatus(TOP_BTN_ONE);
					break;

				case R.id.btn_02:
					setTopBtnStatus(TOP_BTN_TWO);
					break;
				case R.id.btn_03:
					setTopBtnStatus(TOP_BTN_THREE);
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onClickBar(View v) {

		if (externView.getTag() != null) {
			((View) (externView.getTag())).setSelected(false);
		}
		externView.setTag(v);
		v.setSelected(true);

		switch (v.getId()) {
		case R.id.btn_01:
			mPager.setCurrentItem(TOP_BTN_ONE);
			break;
		case R.id.btn_02:
			mPager.setCurrentItem(TOP_BTN_TWO);
			break;
		case R.id.btn_03:
			mPager.setCurrentItem(TOP_BTN_THREE);
			break;
		}
	}

	static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		private final Context mContext;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(Class<?> _class, Bundle _args) {
				clss = _class;
				args = _args;
			}
		}

		public MyFragmentPagerAdapter(FragmentManager fm, Activity activity) {
			super(fm);
			mContext = activity;
		}

		// 加载Tab数据和类
		public void addTab(Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			mTabs.add(info); // 操作类(Fragement Activity)
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int pos) {
			TabInfo info = mTabs.get(pos); // 获得SetTag的类等信息
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args); // Fragment对应的activity类
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}
	}

	/*private void listItemScrollLocate(float ratio, int curIndex, int direct,
			int startOrEnd) {
		if (curIndex < 0 || curIndex >= TOP_BTN_NUMBER) {
			return;
		}

		Fragment fragment = (Fragment) mPager.getAdapter().instantiateItem(
				mPager, curIndex);
		if (fragment instanceof AnimationFragment) {
			AnimationFragment nf = (AnimationFragment) fragment;
			nf.scrollLocate(direct, startOrEnd);
		}
	}

	private void listItemScroll(float ratio, int curIndex, int originDirect,
			int direct) {
		if (curIndex < 0 || curIndex >= TOP_BTN_NUMBER) {
			return;
		}
		Fragment fragment = (Fragment) mPager.getAdapter().instantiateItem(
				mPager, curIndex);
		if (fragment instanceof AnimationFragment) {
			AnimationFragment nf = (AnimationFragment) fragment;
			nf.scrollTo(ratio, originDirect, direct);
		}
	}
*/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		FragmentPagerAdapter fAdapter = (FragmentPagerAdapter) mPager
				.getAdapter();
		if (fAdapter == null)
			return;
		for (int i = 0; i < fAdapter.getCount(); i++) {
			Fragment fragment = fAdapter.getItem(i);
			fragment.onDetach();
		}
	}

}
