package com.example.demos.retrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demos.R;
import com.example.demos.retrofit.bean.Translation;
import com.example.demos.retrofit.rxjava.BaseObserver;
import com.example.demos.retrofit.rxjava.bean.BaseBean;
import com.example.demos.retrofit.rxjava.bean.Translation2;
import com.example.demos.retrofit.rxjava.bean.Translation3;
import com.example.demos.retrofit.rxjava.biz.TranslateBiz;
import com.example.demos.retrofit.rxjava.util.HttpClient1;
import com.example.demos.retrofit.service.TranslateService;
import com.example.demos.retrofit.util.HttpClient;
import com.example.demos.util.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;



public class Main2Activity extends AppCompatActivity {

    public static final String API_URL = "http://fy.iciba.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //request();
        //requestPost();


        HttpClient.init();
        Button button = (Button) findViewById(R.id.btn_01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TranslateService translateService = HttpClient.getRetrofit().create(TranslateService.class);
                //TranslateService translateService = HttpClient.initWithHeader().create(TranslateService.class);


                //Retrofit使用
                TranslateService translateService = HttpClient.creatService(TranslateService.class);
                Call<com.example.demos.retrofit.bean.Translation> call = translateService.getCall();
                call.enqueue(new Callback<com.example.demos.retrofit.bean.Translation>() {

                    @Override
                    public void onResponse(Call<com.example.demos.retrofit.bean.Translation> call, Response<com.example.demos.retrofit.bean.Translation> response) {
                        com.example.demos.retrofit.bean.Translation translation = response.body();
                        translation.show();
                    }

                    //请求失败时回调
                    @Override
                    public void onFailure(Call<com.example.demos.retrofit.bean.Translation> call, Throwable throwable) {
                        System.out.println("连接失败");
                    }
                });
            }
        });


        HttpClient1.init();
        final Button button2 = (Button) findViewById(R.id.btn_02);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateBiz translateBiz = new TranslateBiz();
                //get 请求
                /*translateBiz.getTranlateResult(new Subscriber<Translation2>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Translation2 translation2) {
                        translation2.show();
                    }
                });*/

                //Retrofit + RXJava 使用
                String str = "I love you";
                translateBiz.getTranlateResultPost1(new Observer<Translation3>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Translation3 value) {
                        System.out.println(value.getTranslateResult().get(0).get(0).getTgt());
                        button2.setText(value.getTranslateResult().get(0).get(0).getTgt());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }, str);

                translateBiz.getTranlateResultPost(new BaseObserver<Translation3>(Main2Activity.this, true){

                    @Override
                    public void onSuccess(Translation3 t) {
                        System.out.println(t.getTranslateResult().get(0).get(0).getTgt());
                        button2.setText(t.getTranslateResult().get(0).get(0).getTgt());
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                } ,str);


               /*
                translateBiz.getTranlateResultPost(new Subscriber<Translation3>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "请求出错", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Translation3 translation2) {
                        System.out.println(translation2.getTranslateResult().get(0).get(0).getTgt());
                        button2.setText(translation2.getTranslateResult().get(0).get(0).getTgt());
                    }
                }, str);*/
            }
        });

    }


    public static class Translation {
        private int status;

        private content content;
        private static class content {
            private String from;
            private String to;
            private String vendor;
            private String out;
            private int errNo;
        }

        //定义 输出返回数据 的方法
        public void show() {
            System.out.println(status);

            System.out.println(content.from);
            System.out.println(content.to);
            System.out.println(content.vendor);
            System.out.println(content.out);
            System.out.println(content.errNo);
        }
    }

    //get 请求测试
    public interface GetRequest_Interface {

        @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
        Call<Translation> getCall();
        // 注解里传入 网络请求 的部分URL地址
        // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
        // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
        // getCall()是接受网络请求数据的方法

        //传入请求参数
        @GET("ajax.php?a=fy&f=auto&t=auto")
        Call<Translation> getCall(@Query("w") String w);
    }


    /**
     * get 请求
     */
    public void request() {

        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //自定义OkHttpClient
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        //添加拦截器
        okHttpClient.addInterceptor(httpLoggingInterceptor);

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<Translation> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 步骤7：处理返回的数据结果
                //response.body().show();
                Translation translation = response.body();
                translation.show();
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });


        //对 发送请求 进行封装
        Call<Translation> call1 = request.getCall("hello world");

        //步骤6:发送网络请求(异步)
        call1.enqueue(new Callback<Translation>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 步骤7：处理返回的数据结果
                //response.body().show();
                Translation translation = response.body();
                translation.show();
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });


    }


    /**
     * post 请求
     */

    public static class Translation1 {

        private String type;
        private int errorCode;
        private int elapsedTime;
        private List<List<TranslateResultBean>> translateResult;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public int getElapsedTime() {
            return elapsedTime;
        }

        public void setElapsedTime(int elapsedTime) {
            this.elapsedTime = elapsedTime;
        }

        public List<List<TranslateResultBean>> getTranslateResult() {
            return translateResult;
        }

        public void setTranslateResult(List<List<TranslateResultBean>> translateResult) {
            this.translateResult = translateResult;
        }

        public static class TranslateResultBean {
            /**
             * src : merry me
             * tgt : 我快乐
             */

            public String src;
            public String tgt;

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getTgt() {
                return tgt;
            }

            public void setTgt(String tgt) {
                this.tgt = tgt;
            }
        }

    }

    public interface PostRequest_Interface {

        @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
        @FormUrlEncoded
        //@Headers("t:1")
        Call<Translation1> getCall(@Field("i") String targetSentence);
        //采用@Post表示Post方法进行请求（传入部分url地址）
        // 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
        // 需要配合@Field 向服务器提交需要的字段
    }

    /**
     * post 请求
     */
    public void requestPost() {

        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        //自定义OkHttpClient
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        //添加拦截器
        okHttpClient.addInterceptor(httpLoggingInterceptor);
        okHttpClient.addInterceptor(new CommonInterceptor());


        //设置超时
        okHttpClient.connectTimeout(15, TimeUnit.SECONDS);
        okHttpClient.readTimeout(20, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        okHttpClient.retryOnConnectionFailure(true);


        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();


        // 步骤5:创建 网络请求接口 的实例
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

        //对 发送请求 进行封装(设置需要翻译的内容)
        Call<Translation1> call = request.getCall("I love you");

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation1>() {

            //请求成功时回调
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                // 步骤7：处理返回的数据结果：输出翻译的内容
                System.out.println(response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation1> call, Throwable throwable) {
                System.out.println("请求失败");
                System.out.println(throwable.getMessage());
            }
        });
    }


    /**
     * 设置通用请求参数
     */
    public class CommonInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();

            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter("token", "test001");

            // 新的请求
            Request newRequest = oldRequest.newBuilder()
                    .header("headertest", "head001")
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .build();

            return chain.proceed(newRequest);
        }
    }


    /**
     * 设置缓存
     */
    public void setCache(){
        //设置缓存目录
        File cacheFile = new File(this.getExternalCacheDir(), "test_cache");
        //生成缓存，50M
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        //缓存拦截器
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //网络不可用
                if (!NetUtils.hasInternet(getApplicationContext())) {
                    //在请求头中加入：强制使用缓存，不访问网络
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                okhttp3.Response response = chain.proceed(request);
                //网络可用
                if (NetUtils.hasInternet(getApplicationContext())) {
                    int maxAge = 0;
                    // 有网络时 在响应头中加入：设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    // 无网络时，在响应头中加入：设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                return response;
            }
        };

    }

}
//http://192.168.20.79:9000/resources/ED/100/e7173a1e-f859-4a71-9c97-ebd1ba2b5b05.png


