package com.example.demos.okhttp3.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.demos.R;
import com.example.demos.okhttp3.HttpClient;
import com.example.demos.retrofit.rxjava.bean.Translation3;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 公司：大商道商品交易市场股份有限公司
 * 项目：大商道离岸交易平台
 * 简述：XXX
 * 作者：Chenxp
 * 时间：2018/3/14
 * 版本：V1.0.0
 */
public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);

        HttpClient.getInstance().init(this);
        final OkHttpClient okHttpClient = HttpClient.getInstance().getOkHttpClient();

        final EditText editText = (EditText) findViewById(R.id.editText1);

        //get请求
        Button button = (Button) findViewById(R.id.btn_01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world";

                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme("http")
                        .host("fy.iciba.com")
                        //.addPathSegment("user")
                        .addPathSegment("ajax.php")
                        .addQueryParameter("a", "fy")
                        .addQueryParameter("f","auto")
                        .addQueryParameter("t","auto")
                        .addQueryParameter("w","hello world")
                        .build();
                //两种请求参数用法
                Request req = new Request.Builder()
                        //注意的是：okhttp只会对get请求进行缓存，post请求是不会进行缓存，这也是有道理的，因为get请求的数据一般是比较持久的，而post一般是交互操作，没太大意义进行缓存。
                        .cacheControl(new CacheControl.Builder().maxStale(20, TimeUnit.SECONDS).build())
                        //.cacheControl(new CacheControl.Builder().maxAge(20, TimeUnit.SECONDS).build())
                        .url(httpUrl.url()).build();

                        //Request req = new Request.Builder().url(url).build();
                Call call= okHttpClient.newCall(req);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //成功回调，当前线程为子线程，如果需要更新UI，需要post到主线程中
                        boolean successful = response.isSuccessful();
                        //响应消息头
                        Headers headers = response.headers();
                        //响应消息体
                        ResponseBody body = response.body();
                        final String content=response.body().string();
                        //缓存控制
                        CacheControl cacheControl = response.cacheControl();

                        System.out.println(content);

                        Main3Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                editText.setText(content);
                            }
                        });
                    }
                });
            }
        });

        //post 请求
        Button button2 = (Button) findViewById(R.id.btn_02);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //json 请求
                /*String url = "";
                String jsonStr = "";
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStr);
                Request req = new Request.Builder().url(url)
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(req);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });*/

                //表单POST请求
                String url1 = "http://fanyi.youdao.com/translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=";
                RequestBody requestBody1 =
                        new FormBody.Builder()
                                .add("i", "I like you")
                                .build();
                Request req1 = new Request.Builder().url(url1).post(requestBody1).build();
                Call call1 = okHttpClient.newCall(req1);
                call1.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody body = response.body();
                        final String content=response.body().string();
                        System.out.println(content);
                        Main3Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                editText.setText(content);
                            }
                        });
                    }
                });

                //文件上传
                /*String url3 = "";
                String fileName = "111";
                File file = new File("xxx");
                RequestBody uploadImg = RequestBody.create(MediaType.parse("image/jpeg"), file);
                MultipartBody uploadBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("username", "zhangsan")
                        .addFormDataPart("password", "11111")
                        .addFormDataPart("img", fileName, uploadImg)
                        .build();
                Request req = new Request.Builder().url(url3).post(uploadBody).build();
                okHttpClient.newCall(req);*/
            }
        });

    }
}
