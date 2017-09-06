package com.example.gethttp;

import MyFunction.UrlThread;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WebActivity extends Activity {

	Button Bn_Next;
	Button Bn_Per;
	EditText Ed_strUrl;
	WebView webView;
	Button Bn_Update;
	
	UrlThread urlThread;
	
	//public static String str_url = "http://mini.eastday.com/mobile/170726123117522.html";
	public static String str_url = "http://www.yinews.cn/article/4797117.shtm";
//	public static String str_url = "http://flv.bn.netease.com/videolib3/1411/24/Eqwbg0292/SD/movie_index.m3u8";
	private OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0==Bn_Next){
				Intent intent = new Intent(WebActivity.this, NextActivity.class);
				startActivity(intent);
			}else if(arg0==Bn_Per){
				Intent intent = new Intent(WebActivity.this, JsonActivity.class);
				startActivity(intent);
			}else if(arg0==Bn_Update){
				//请求url 网页 页面加载
				String str = Ed_strUrl.getText().toString();
				if("".equals(str)==false) str_url = str;
				//启动  
				webView.loadUrl(str_url);
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
		setContentView(R.layout.activity_web); 
		
		Bn_Next = (Button)findViewById(R.id.Bn_Next);
		Bn_Per = (Button)findViewById(R.id.Bn_Pre);
		Ed_strUrl = (EditText)findViewById(R.id.Ed_Url);
		webView = (WebView)findViewById(R.id.web_id);
		Bn_Update = (Button)findViewById(R.id.Bn_update);
		
		Bn_Next.setOnClickListener(l);
		Bn_Per.setOnClickListener(l);
		Bn_Update.setOnClickListener(l);
		
		Ed_strUrl.setHint(str_url);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				// TODO Auto-generated method stub
				//return super.shouldOverrideUrlLoading(view, url);
				return true;
			}			
		});
	}
}
