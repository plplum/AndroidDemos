package com.example.demos.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demos.R;

public class JsonParseActivity extends Activity implements OnClickListener{
	
	private EditText editText;
	private Dialog mLoadingDialog;
	private Button button;
	private TextView textView;
	
	private String sampleData1 = "{"
			+ "   \"phone\" : [\"12345678\", \"87654321\"],"
			+ "   \"name\" : \"yuanzhifei89\","
			+ "   \"age\" : 100,"
			+ "   \"address\" : { \"country\" : \"china\", \"province\" : \"jiangsu\" },"
			+ "   \"married\" : false" + "}";
	
	
	
	String data = "{"
			+ "    \"action\": \"getMixWeather\","
    + "   \"code\": 0,"
    + "   \"msg\": \"success\","
    + "   \"detail\": {"
        + "   \"latest\": {"
            + "   \"datatime\": \"2015-11-17 05:34:22\","
            + "   \"at\": \"17.7\","
            + "   \"wd\": \"117\","
            + "   \"ws\": \"2.9\","
            + "   \"ah\": \"75.4\""
            + "   },"
            + "   \"stat\": {"
            + "   \"TOTALCOUNT\": 3,"
            + "   \"ROOT\": ["
                + "   {"
                    + "   \"min\": 15,"
                    + "   \"max\": 18,"
                    + "   \"datatime\": \"2015-11-11\","
                    + "   \"mpointid\": \"200000084\","
                    + "   \"avg\": 16.6"
                    + "   },"
                + "   {"
                    + "   \"min\": 15,"
                    + "   \"max\": 19,"
                    + "   \"datatime\": \"2015-11-12\","
                    + "   \"mpointid\": \"200000084\","
                    + "   \"avg\": 17"
                    + "   },"
                + "   {"
                 + "      \"min\": 15,"
                 + "      \"max\": 17,"
                + "       \"datatime\": \"2015-11-13\","
                + "       \"mpointid\": \"200000084\","
                 + "      \"avg\": 16.2"
                 + "    }"
            + "   ]"
            + "    }"
            + "    }"
            + "   }";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_jsonparse);
		
		editText = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);

		textView = (TextView) findViewById(R.id.textView3);
		
		mLoadingDialog = new Dialog(JsonParseActivity.this, R.style.loadingProgressDialog);
		mLoadingDialog.setContentView(R.layout.loading_progress_bar);
		mLoadingDialog.setCancelable(false);
		
		JSONObject jsonObject = creatJson();
		sampleData1 = jsonObject.toString();
		editText.setText(sampleData1);
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				textView.setText(result);
				break;

			default:
				break;
			}
		};
	};
	
	class JsonParseThread extends Thread {
		public void run() {
			StringBuffer buffer = new StringBuffer();
			try {
				JSONTokener jsonParser = new JSONTokener(sampleData1);
				JSONObject person = (JSONObject) jsonParser.nextValue();
				JSONArray jsonArray = person.getJSONArray("phone");
				String phone = jsonArray.getString(0) + "--" + jsonArray.getString(1);
				String name = person.getString("name");
				int age = person.getInt("age");
				JSONObject addObj = person.getJSONObject("address");
				String country = addObj.getString("country");
				String province = addObj.getString("province");
				boolean married = person.getBoolean("married");
				
				buffer.append(name + " " + phone + " " + age + " " + country + " " + province + " " + married);
				
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
			Message message = new Message();
			message.obj = buffer.toString();
			message.what = 0;
			handler.sendMessage(message);
			mLoadingDialog.dismiss();
		};
	}
	
	
	/**
	 * create JSONObject
	 * @return json object
	 */
	private JSONObject creatJson() {
		JSONObject person = null;
	    try {  
	        // 首先最外层是{}，是创建一个对象  
	        person = new JSONObject();  
	        // 第一个键phone的值是数组，所以需要创建数组对象  
	        JSONArray phone = new JSONArray();  
	        phone.put("12345678").put("87654321");  
	        person.put("phone", phone);  
	        person.put("name", "yuanzhifei89");  
	        person.put("age", 100);  
	        // 键address的值是对象，所以又要创建一个对象  
	        JSONObject address = new JSONObject();  
	        address.put("country", "china");  
	        address.put("province", "jiangsu");  
	        person.put("address", address);    
	        person.put("married", false);  
	    } catch (JSONException ex) {  
	        throw new RuntimeException(ex);  
	    }
		return person;  
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			mLoadingDialog.show();
			//thread.start();
			JsonParseThread parseThread = new JsonParseThread();
			parseThread.start();
			break;

		default:
			break;
		}
		
	}

}
