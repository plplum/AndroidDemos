package com.example.demos.http.util;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtilApache {
	
	
	
	public static String request(String method, String url) {

		HttpResponse httpResponse = null;

		StringBuffer result = new StringBuffer();

		try {

			if (method.equals("GET")) {

				// 1.通过url创建HttpGet对象

				HttpGet httpGet = new HttpGet(url);

				// 2.通过DefaultClient的excute方法执行返回一个HttpResponse对象

				HttpClient httpClient = new DefaultHttpClient();

				httpResponse = httpClient.execute(httpGet);
				
				// 3.取得相关信息

				// 取得HttpEntiy

				HttpEntity httpEntity = httpResponse.getEntity();
				

				// 得到一些数据

				// 通过EntityUtils并指定编码方式取到返回的数据

				result.append(EntityUtils.toString(httpEntity, "utf-8"));

				// 得到StatusLine接口对象

				//StatusLine statusLine = httpResponse.getStatusLine();

				// 得到协议

				//result.append("协议:" + statusLine.getProtocolVersion() + "\r\n");

				//int statusCode = statusLine.getStatusCode();

				//result.append("状态码:" + statusCode + "\r\n");

			} else if (method.equals("POST")) {

				// 1.通过url创建HttpGet对象

				HttpPost httpPost = new HttpPost(url);

				// 2.通过DefaultClient的excute方法执行返回一个HttpResponse对象

				HttpClient httpClient = new DefaultHttpClient();

				httpResponse = httpClient.execute(httpPost);

				// 3.取得相关信息

				// 取得HttpEntiy

				HttpEntity httpEntity = httpResponse.getEntity();

				// 得到一些数据

				// 通过EntityUtils并指定编码方式取到返回的数据

				result.append(EntityUtils.toString(httpEntity, "utf-8"));

				/*StatusLine statusLine = httpResponse.getStatusLine();

				statusLine.getProtocolVersion();

				int statusCode = statusLine.getStatusCode();

				result.append("状态码:" + statusCode + "\r\n");*/

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();

	}

	
	public static InputStream getInputStream(String url){
		InputStream is = null;
		try {
			HttpResponse httpResponse = null;
			// 1.通过url创建HttpGet对象

			HttpGet httpGet = new HttpGet(url);

			// 2.通过DefaultClient的excute方法执行返回一个HttpResponse对象

			HttpClient httpClient = new DefaultHttpClient();

			httpResponse = httpClient.execute(httpGet);
			
			// 3.取得相关信息

			// 取得HttpEntiy

			HttpEntity httpEntity = httpResponse.getEntity();
			
			is = httpEntity.getContent();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return is;
	}
	
	
	
	
}
