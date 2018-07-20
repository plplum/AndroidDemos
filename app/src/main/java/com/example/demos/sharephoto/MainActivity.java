package com.example.demos.sharephoto;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.demos.R;


public class MainActivity extends Activity {
    private ListView listView;
    private List<List<Image>> imagesList;

    
    private NineGridlayout gridlayout;
    
    
    private String[][] images=new String[][]{{
             "http://img2.imgtn.bdimg.com/it/u=194240101,2532182839&fm=21&gp=0.jpg","640","960"}
            ,{"file:///android_asset/img2.jpg","250","250"}
            ,{"file:///android_asset/img3.jpg","250","250"}
            ,{"file:///android_asset/img4.jpg","250","250"}
            ,{"file:///android_asset/img5.jpg","250","250"}
            ,{"file:///android_asset/img6.jpg","250","250"}
            ,{"file:///android_asset/img7.jpg","250","250"}
            ,{"file:///android_asset/img8.jpg","250","250"}
            ,{"http://img.taopic.com/uploads/allimg/130501/240451-13050106450911.jpg","1280","800"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sharephoto);

        listView= (ListView) findViewById(R.id.lv_main);
        initData();
        listView.setAdapter(new MainAdapter(MainActivity.this, imagesList));
    }

    private void initData() {
        imagesList=new ArrayList<List<Image>>();
       //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<Image> singleList=new ArrayList<Image>();
        singleList.add(new Image(images[8][0],Integer.parseInt(images[8][1]),Integer.parseInt(images[8][2])));
        imagesList.add(singleList);
        //从一到9生成9条朋友圈内容，分别是1~9张图片
        for(int i=0;i<9;i++){
            ArrayList<Image> itemList=new ArrayList<Image>();
             for(int j=0;j<=i;j++){
                 itemList.add(new Image(images[j][0],Integer.parseInt(images[j][1]),Integer.parseInt(images[j][2])));
             }
            imagesList.add(itemList);
        }
    }

}
