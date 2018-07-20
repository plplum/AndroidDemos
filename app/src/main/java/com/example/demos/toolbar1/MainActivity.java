package com.example.demos.toolbar1;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.example.demos.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/***
 * 详细代码说明见csdn：http://blog.csdn.net/u011343735/article/details/53761170
 */
public class MainActivity extends AppCompatActivity implements OnBannerListener {


    private View layout;
    private FadingScrollView fadingScrollView;

    private SmartRefreshLayout smartRefreshLayout;

    private FrameLayout frameLayout;

    Banner banner;

    RecyclerView recyclerView;

    AdminAdapter adapter;

    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        StatusBarUtil.immersive(this);

        setContentView(R.layout.activity_toolbar1_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        banner = (Banner) findViewById(R.id.banner);


        adapter = new AdminAdapter(this, list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        for (int i = 0; i <50 ; i++) {
            list.add("test" + i);
        }
        adapter.notifyDataSetChanged();



        layout = findViewById(R.id.nac_layout);
        layout.setAlpha(0);

        fadingScrollView = (FadingScrollView)findViewById(R.id.nac_root);
        fadingScrollView.setFadingView(layout);
        fadingScrollView.setFadingHeightView(banner);

        initBanner();

        frameLayout = (FrameLayout) findViewById(R.id.layout1);

        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);

        smartRefreshLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                /*if (offset>headerHeight){
                    frameLayout.setVisibility(View.INVISIBLE);
                }*/
                frameLayout.setVisibility(View.INVISIBLE);

                System.out.println("111111111111111");
                System.out.println("offset = " + offset + " headerHeight = " + headerHeight);
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int extendHeight) {
                //System.out.println("222222222222222");
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                //System.out.println("333333333333333333");
            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {
                //System.out.println("44444444444444444");
            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                //System.out.println("55555555555555555");
               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frameLayout.setVisibility(View.VISIBLE);
                    }
                }, 500);*/

            }

            @Override
            public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                System.out.println("6666666666666666666");
                refreshlayout.finishRefresh(2000);

            }

            @Override
            public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

                System.out.println(oldState.name() +"--- " + newState.name());

                System.out.println(refreshLayout.isRefreshing());
                if ("RefreshFinish".equals(newState.name())){
                    //frameLayout.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            frameLayout.setVisibility(View.VISIBLE);
                        }
                    }, 500);
                }
                if ("PullDownCanceled".equals(oldState.name())){
                    frameLayout.setVisibility(View.VISIBLE);
                }

                System.out.println("7777777777777777");

            }
        });

       /* smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                frameLayout.setVisibility(View.GONE);
            }
        });*/

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


    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
