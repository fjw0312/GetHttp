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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class NextActivity extends Activity {

	Button Bn_Next;
	Button Bn_Per;
	EditText Ed_strUrl;
	ImageView imageView;
	Button Bn_Update;
	
	UrlThread urlThread;
	
//	public static String str_url = "http://www.mob.com/public/images/index/banner1.jpg";
//	public static String str_url = "http://09.imgmini.eastday.com/mobile/20170726/20170726123117_50e734c9e96abb034a1d5c952acae1a8_21_mwpm_03200403.jpg";
//	public static String str_url = "http://mmbiz.qpic.cn/mmbiz_jpg/Ne6FancVlWjF9PLSuNt7HrJV4we42icIVRDQ88ly0g191lwCjYP718qjn9YxJAA0Y3xLQL4LwlNsQregjj3PhFw/0?wx_fmt=jpeg";
	public static String str_url = "http://img3.cache.netease.com/3g/2014/11/13/20141113170743f92db.jpg";
	
	private OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0==Bn_Next){
				Intent intent = new Intent(NextActivity.this, VideoActivity.class);
				startActivity(intent);
			}else if(arg0==Bn_Per){
				Intent intent = new Intent(NextActivity.this, WebActivity.class);
				startActivity(intent);
			}else if(arg0==Bn_Update){
				//请求url 网络图片加载
				String str = Ed_strUrl.getText().toString();
				if("".equals(str)==false) str_url = str;
				//启动  请求线程
				urlThread = new UrlThread(str_url,myHandler);
				urlThread.start();
			}
		}
	};
	
	private Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 1:    //图片获取成功
				imageView.setImageBitmap(urlThread.bitmap);
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
		setContentView(R.layout.activity_next); 
		
		Bn_Next = (Button)findViewById(R.id.Bn_Next);
		Bn_Per = (Button)findViewById(R.id.Bn_Pre);
		Ed_strUrl = (EditText)findViewById(R.id.Ed_Url);
		imageView = (ImageView)findViewById(R.id.Iv_image);
		Bn_Update = (Button)findViewById(R.id.Bn_update);
		
		Bn_Next.setOnClickListener(l);
		Bn_Per.setOnClickListener(l);
		Bn_Update.setOnClickListener(l);
		
		Ed_strUrl.setHint(str_url);
	}
}
