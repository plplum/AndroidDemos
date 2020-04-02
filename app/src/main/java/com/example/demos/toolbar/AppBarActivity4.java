package com.example.demos.toolbar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demos.R;
import com.example.demos.loadimages.ToolImage;
import com.example.demos.toolbar1.GlideImageLoader;
import com.example.demos.util.ScreenUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.stx.xhb.xbanner.entity.SimpleBannerInfo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司：深圳市中泰智丰物联网科技有限公司
 * 项目：智能家居APP
 * 简述：XXX页面
 * 作者：Chenxp
 * 时间：2017/7/22
 * 版本：V0.0.1
 */
public class AppBarActivity4 extends AppCompatActivity implements OnBannerListener {

	AppBarLayout appBarLayout;

	LinearLayout headLayout;

	CollapsingToolbarLayout collapsingToolbarLayout;

	Toolbar toolbar;

	Banner banner;

	TextView textView;

	private TabLayout mTabLayout;
	private ViewPager mViewPager;

	private Fragment[] mFragmentArrays = new Fragment[6];
	private String[] mTabTitles = new String[6];

	//获取控件
	XBanner mXBanner;

	private com.nostra13.universalimageloader.core.ImageLoader universalimageloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main44);
//状态栏设置透明
		//StatusBarUtil.setTransparent(AppBarActivity2.this);

		//Utils.setStatusBar(AppBarActivity4.this, false, true);

		universalimageloader = ToolImage.initImageLoader(getApplicationContext());

		toolbar = (Toolbar) findViewById(R.id.toolbar);

		//toolbar.setTitle("This is Title");
		//toolbar.setNavigationIcon(R.drawable.back);
		setSupportActionBar(toolbar);
		//toolbar.setNavigationOnClickListener(v -> System.out.println("1111222222222"));



		textView = (TextView) findViewById(R.id.toolbar_edit);
		//textView.setOnClickListener(v -> System.out.println("33333333333333333333333")
		//);


		banner = (Banner) findViewById(R.id.banner);
		initBanner();


		appBarLayout = (AppBarLayout) findViewById(R.id.AppBarLayout01);

		headLayout = (LinearLayout) findViewById(R.id.head_layout);

		collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collLayout);

		setTitleToCollapsingToolbarLayout();



		mTabTitles[0] = "Menu11";
		mTabTitles[1] = "Menu22";
		mTabTitles[2] = "Menu33";
		mTabTitles[3] = "Menu44";
		mTabTitles[4] = "Menu55";
		mTabTitles[5] = "Menu66";

		mFragmentArrays[0] = AuthorInfoFragment.newInstance();
		mFragmentArrays[1] = AuthorInfoFragment.newInstance();
		mFragmentArrays[2] = AuthorInfoFragment.newInstance();
		mFragmentArrays[3] = AuthorInfoFragment.newInstance();
		mFragmentArrays[4] = AuthorInfoFragment.newInstance();
		mFragmentArrays[5] = AuthorInfoFragment.newInstance();



		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		PagerAdapter pagerAdapter = new AuthorPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(pagerAdapter);

		//初始化TabLayout，增加Tab，同时关联ViewPager
		mTabLayout = (TabLayout) findViewById(R.id.tabs);
		mTabLayout.setupWithViewPager(mViewPager);

		mXBanner = (XBanner) findViewById(R.id.banner3);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(this) / 2);
		mXBanner.setLayoutParams(layoutParams);

		initBanner(mXBanner);

		//网络
		//initData();
		//本地
		initLocalImage();



/*
		ImageView imageView = (ImageView) findViewById(R.id.back);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("2b");
			}
		});


		TextView textView = (TextView) findViewById(R.id.toolbar_edit);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("3333333333333");
			}
		});*/
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		//作用同setNavigationOnClickListener
		if (item.getItemId() == R.id.back){
			System.out.println("1111222222222");
		}
		return super.onOptionsItemSelected(item);

	}*/

	/**
	 * 使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，
	 * 设置到Toolbar上则不会显示
	 */
	private void setTitleToCollapsingToolbarLayout() {

		//collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.black));
		//collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));


		appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				if (verticalOffset <= -headLayout.getHeight() / 2) {
					collapsingToolbarLayout.setTitle("This is Title");
					//使用下面两个CollapsingToolbarLayout的方法设置展开透明->折叠时你想要的颜色
					//collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
					//collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
				} else {
					collapsingToolbarLayout.setTitle("This is Title");
					//collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));

				}
			}
		});
	}

	private void initBanner(){
		//加载本地资源方法
		Uri uri1 = resourceIdToUri(this, R.drawable.banner_a);
		Uri uri2 = resourceIdToUri(this, R.drawable.banner_b);
		Uri uri3 = resourceIdToUri(this, R.drawable.banner_c);
		List<Uri> imagesUrl = new ArrayList<>();
		imagesUrl.add(uri1);
		imagesUrl.add(uri2);
		imagesUrl.add(uri3);

		//轮播广告
        /*List<String> imagesUrl = new ArrayList<>();
        imagesUrl.add(BANNER_URL1);
        imagesUrl.add(BANNER_URL2);
        imagesUrl.add(BANNER_URL3);*/
		//Banner banner = (Banner) view.findViewById(R.id.banner);
		banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
		banner.setImageLoader(new GlideImageLoader());
		banner.setImages(imagesUrl);
		banner.setBannerAnimation(Transformer.Default);
		//banner.setBannerTitles(titles);
		banner.isAutoPlay(true);
		banner.setDelayTime(5000);
		banner.setIndicatorGravity(BannerConfig.CENTER);
		banner.setOnBannerListener(this);
		banner.start();
	}

	private class AuthorPagerAdapter extends FragmentPagerAdapter {

		public AuthorPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentArrays[position];
		}

		@Override
		public int getCount() {
			return mFragmentArrays.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mTabTitles[position];
		}
	}

	public static final String ANDROID_RESOURCE = "android.resource://";
	public static final String FOREWARD_SLASH = "/";
	public static Uri resourceIdToUri(Context context, int resourceId) {
		return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
	}

	@Override
	public void OnBannerClick(int position) {

	}









	/**
	 * 初始化XBanner
	 */
	private void initBanner(XBanner banner) {
		//设置广告图片点击事件
		banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
			@Override
			public void onItemClick(XBanner banner, Object model, View view, int position) {
				Toast.makeText(AppBarActivity4.this, "点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT).show();
			}
		});


		//加载广告图片
		banner.loadImage(new XBanner.XBannerAdapter() {
			@Override
			public void loadBanner(XBanner banner, Object model, View view, int position) {
				/*BannerData datas = (BannerData) model;
				ImageView draweeView = (ImageView) view;
				universalimageloader.displayImage((String) datas.getXBannerUrl(),
						draweeView, ToolImage.getFadeOptions(
								R.drawable.default_icon, R.drawable.ic_launcher,
								R.drawable.ic_launcher));*/

//              加载本地图片展示
                ((ImageView)view).setImageResource(((LocalImageInfo) model).getXBannerUrl());
			}
		});
	}


	class BannerData extends SimpleBannerInfo {

		Object url;

		public Object getUrl() {
			return url;
		}

		public void setUrl(Object url) {
			this.url = url;
		}

		@Override
		public Object getXBannerUrl() {
			return url;
		}
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		//加载网络图片资源
		//刷新数据之后，需要重新设置是否支持自动轮播
		//mXBanner.setAutoPlayAble(data.size() > 1);
		List<BannerData> datas = new ArrayList<>();
		BannerData data1 = new BannerData();
		data1.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585820241902&di=e9ffdb839546c0864978b53a382bc547&imgtype=0&src=http%3A%2F%2Fpic23.nipic.com%2F20120814%2F10618619_161417472000_2.jpg");

		BannerData data2 = new BannerData();
		data2.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585820241903&di=0166c4a0b24c222467501f8475c3c865&imgtype=0&src=http%3A%2F%2Fpic17.nipic.com%2F20111015%2F3191817_155116009376_2.jpg");

		datas.add(data1);
		datas.add(data2);
		datas.add(data1);
		datas.add(data2);

		mXBanner.setAutoPlayAble(true);
		//mXBanner.setIsClipChildrenMode(true);
		mXBanner.setBannerData(R.layout.layout_fresco_imageview, datas);

	}

	/**
	 * 加载本地图片
	 */
	private void initLocalImage() {
		List<LocalImageInfo> data = new ArrayList<>();
		data.add(new LocalImageInfo(R.drawable.banner_a));
		data.add(new LocalImageInfo(R.drawable.banner_b));
		data.add(new LocalImageInfo(R.drawable.banner_c));
		mXBanner.setBannerData(data);
		mXBanner.setAutoPlayAble(true);
	}

}
