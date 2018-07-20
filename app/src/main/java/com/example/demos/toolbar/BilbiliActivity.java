package com.example.demos.toolbar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demos.R;

public class BilbiliActivity extends AppCompatActivity {

    private AppBarLayout Appbar_main;
    private CollapsingToolbarLayout Ctl_toolbar;
    private ButtonBarLayout btn_Play;
    private TextView txt_bilibilicontent;

    private ImageView img_Head;
    private ImageView img_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilbili);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("心灵鸡汤");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initControls();
    }

    /**
     * 初始化控件
     */
    private void initControls() {

        Appbar_main = (AppBarLayout)findViewById(R.id.Appbar_main);
        Ctl_toolbar = (CollapsingToolbarLayout)findViewById(R.id.Ctl_toolbar);

        img_Head = (ImageView)findViewById(R.id.img_Head);
        img_two = (ImageView)findViewById(R.id.img_two);

        btn_Play = (ButtonBarLayout)findViewById(R.id.btn_Play);
        btn_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img_Head.setVisibility(View.GONE);
                img_two.setVisibility(View.VISIBLE);

                Appbar_main.setExpanded(true);
            }
        });

        state = CollapsingToolbarLayoutState.EXPANDED;

        Appbar_main.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(verticalOffset==0)
                {
                    //全展开
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        //Ctl_toolbar.setTitle("心灵鸡汤");//设置title为EXPANDED
                    }
                }else if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange())
                {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {

                        //Ctl_toolbar.setTitle("34567");//设置title不显示
                        btn_Play.setVisibility(View.VISIBLE);//隐藏播放按钮
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠

                        if(img_two.getVisibility()==View.VISIBLE) {
                            img_Head.setVisibility(View.VISIBLE);
                            img_two.setVisibility(View.GONE);
                        }
                    }
                }else{
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
                            btn_Play.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
                        //Ctl_toolbar.setTitle("洗脑大全");//设置title为INTERNEDIATE
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });




        txt_bilibilicontent = (TextView)findViewById(R.id.txt_bilibilicontent);
        String mContent = "\n    曾经有无数个人从我身边经过，但我只爱上了你，因为她们的脚都是踏在地上，而你的脚却踏在我的心上。从那一刻开始，我就开始认识到，你就是我要找的真爱，是我生命中的唯一。爱你，想时刻关心呵护着你，嫁给我好吗？让我保护你一辈子！\n";
        mContent += "\n    相信爱情可以令一个人改变，是年轻的好处，也是年轻的悲哀。浪子永远是浪子。令男人改变的，也许是上帝的爱或者佛祖的慈悲，但绝对不会是女人。\n";
        mContent += "\n    一个人身边的位置只有那麽多,你能给的也只有那麽多,在这个狭小的圈子里,有些人要进来,就有一些人不得不离开。\n";
        mContent += "\n    谈恋爱最重要的，就是相爱无悔。当初你是怎么爱上的，那就请毫不后悔的好好爱下去。每个人都是有选择爱情的权力，但同时也是有承担结果的义务。既然爱上了一个人，那么你也就是要去接受她可能不尽如人意的那一面。\n";
        mContent += "\n    没有谁能够一直等你，攒够失望的人自然会放手，爱是积累来的，不爱了也是。\n";
        mContent += "\n    我们一生之中，要牢记和要忘记的东西一样多。记忆存在细胞里，在身体里面，与肉体永不分离，要摧毁它，等于玉石俱焚。然而，有些事情必须忘记，忘记痛苦，忘记最爱的人对你的伤害，只好如此。\n";
        mContent += "\n    如果时间不可以令你忘记那些不该记住的人，我们失去的岁月又有什么意义?\n";
        mContent += "\n    可能神要我们在遇到对的人之前先遇上一些错的人，这样当我们遇到生命中真正的人的时候就会更懂得珍惜和感激。\n";
        mContent += "\n    相聚，却来不及牵手；牵手却来不及相爱；相爱却来不及相守…每一个人心中都深藏着一个人，你不知道对方是否生活的好与不好，但有时候，你怀念的却只是一个简单的名字，一段简单的相遇。也许，爱情没有永远，地老天荒也走不完。站在岁月的边端，那些美丽的定格，都被四季的掩埋。 \n";
        txt_bilibilicontent.setText(mContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

}
