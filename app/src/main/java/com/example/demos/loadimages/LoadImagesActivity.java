package com.example.demos.loadimages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.demos.R;

public class LoadImagesActivity extends Activity{
	
	
	private ListView listView;
	
	private ImageListViewAdapter adapter;
	
	private List<Object> mDataList = new ArrayList<Object>();
	
	private String imageURLs[] = new String[]{
			"http://www.daqianduan.com/wp-content/uploads/2014/12/kanjian.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/11/capinfo.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/11/mi-2.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/10/dxy.cn_.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/10/xinhua.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/09/job.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/ctrip.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/09/ideabinder.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/05/ymatou.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/03/west_house.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/03/youanxianpin.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/02/jd.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/11/wealink.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/09/exmail.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/09/alipay.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/08/huaqiangbei.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/ctrip.png",
			"http://www.daqianduan.com/static/img/thumbnail.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/bingdian.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/04/ctrip-wireless.png"
	};
	private String titles[] = new String[]{
			"看见网络科技（上海）有限公司招前端开发工程师",
			"首都信息发展股份有限公司招Web前端工程师(北京-海淀)",
			"小米邀靠谱前端一起玩，更关注用户前端体验(北京)",
			"丁香园求多枚Web前端工程师(杭州滨江 8-15K)",
			"新华网招中高级Web前端开发工程师（北京 8-20K）",
			"好声音母公司梦响强音文化传播招前端、交互和UI设计师(上海)",
			"携程网国际业务部招靠谱前端(HTML+CSS+JS)(上海总部)",
			"ideabinder招聘Web前端开发工程师（JS方向 北京 6-12K）",
			"海外购物公司洋码头招Web前端开发工程师（上海）",
			"金山软件-西山居(珠海)招募前端开发工程师、PHP开发工程师",
			"优安鲜品招Web前端开发工程师(上海)",
			"京东招聘Web前端开发工程师(中/高/资深) 8-22K",
			"若邻网(上海)急聘资深前端工程师",
			"腾讯广州研发线邮箱部门招聘前端开发工程师（内部直招）",
			"支付宝招募资深交互设计师、视觉设计师（内部直招）",
			"华强北商城招聘前端开发工程师",
			"携程(上海)框架研发部招开发工程师(偏前端)",
			"阿里巴巴中文站招聘前端开发",
			"多途网络科技 15K 招聘前端开发工程师",
			"携程无线前端团队招聘 直接内部推荐（携程上海总部）"
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loadimage_main);
		listView = (ListView) findViewById(R.id.image_list);
		// 构造数据
		for (int i = 0; i < 20; i++) {
			Map<String, Object> rowData = new LinkedHashMap<String, Object>();
			rowData.put("imageUrl", imageURLs[i]);
			rowData.put("title", i + 1 + " " + titles[i]);
			mDataList.add(rowData);
		}
		adapter = new ImageListViewAdapter(getApplicationContext(), mDataList);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//清除缓存
		ToolImage.clearCache();
	}

}
