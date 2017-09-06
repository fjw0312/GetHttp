package com.example.gethttp;

import java.util.ArrayList;
import java.util.List;

import MyFunction.UrlThread;
import MyVolley.VolleyHAL;
import MyVolley.Volley_JsonRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class JsonActivity extends Activity {

	Button Bn_Next;
	Button Bn_Per;
	EditText Ed_strUrl;
	ListView listView;
	Button Bn_Update;
	
	List<String> lst_str = new ArrayList<String>();
	ArrayAdapter<String> adapter = null;
	
	UrlThread urlThread;
	
	public static String str_url = "https://v.juhe.cn/toutiao/index?type=guonei&key=d06e07ba96be1937d43de5ccff262cb4";
	
	public void initAdapter(List<String> lst){  
		lst_str = lst;
		adapter = new ArrayAdapter<String>(JsonActivity.this, android.R.layout.simple_list_item_1, lst_str);
		myHandler.sendEmptyMessage(1);
	}

	private OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0==Bn_Next){
				Intent intent = new Intent(JsonActivity.this, WebActivity.class);
				startActivity(intent);
			}else if(arg0==Bn_Per){
				Intent intent = new Intent(JsonActivity.this, MainActivity.class);
				startActivity(intent);
			}else if(arg0==Bn_Update){
				//请求url 页面json
				String str = Ed_strUrl.getText().toString();
				if("".equals(str)==false) str_url = str;
				//启动  
				Volley_JsonRequest request = new Volley_JsonRequest(JsonActivity.this);
				request.getArrayJson_Volley_Get(str_url, "result","data");
			}
		}
	};
	
	private Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 1:   
				listView.setAdapter(adapter);
				break;
			case 2:
				break;
			default:
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏除app标题栏 需放setContentView之前
		setContentView(R.layout.activity_json); 
		 
		Bn_Next = (Button)findViewById(R.id.Bn_Next);  
		Bn_Per = (Button)findViewById(R.id.Bn_Pre);  
		Ed_strUrl = (EditText)findViewById(R.id.Ed_Url); 
		listView = (ListView)findViewById(R.id.lstView);
		Bn_Update = (Button)findViewById(R.id.Bn_update);
		
		Bn_Next.setOnClickListener(l);
		Bn_Per.setOnClickListener(l);
		Bn_Update.setOnClickListener(l);
		
		Ed_strUrl.setHint(str_url);

	}
}
