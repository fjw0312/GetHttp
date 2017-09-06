package com.example.gethttp;

import java.util.zip.Inflater;

import myapplication.MyApplication;

import MyFunction.HttpThread;
import MyFunction.SaveFileThread;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/****
 * 目前需要优化的问题：
 * 1.网络异常 应该  Toast通知
 * 2.网络返回string 会有过大的问题。
 * 
 * 2017.7.20 dubug:将该activity 模式修改为singleTask 
 * */

public class MainActivity extends Activity {

	Button Bn_Exit;
	Button Bn_Next;
	EditText Ed_Url;
	Button Bn_Request;
	TextView Tx_GetContent;
//	TextView Tx_Notice;
	EditText Ed_path;
	EditText Ed_fileName;
	Button Bn_CreateFile;
	
//	public static String str_url = "https://apicloud.mob.com/wx/article/category/query?key=1f5f243035280";
	public static String str_url = "https://apicloud.mob.com/wx/article/search?key=1f5f243035280&cid=1";
//	public static String str_url = "https://v.juhe.cn/toutiao/index?type=top&key=d06e07ba96be1937d43de5ccff262cb4";
//	public static String str_url = "http://api.avatardata.cn/ActNews/LookUp?key=a342376f893b47409f92d635ffab57aa";
//	public static String str_url = "http://api.tianapi.com/meinv/?key=ef3e0ab8a3e7728803b26c7a4efea22e&num=10";
//	public static String str_url = "https://v.juhe.cn/toutiao/index?type=shehui&key=d06e07ba96be1937d43de5ccff262cb4";
//	public static String str_url = "http://c.3g.163.com/nc/video/list/00850FRB/n/20-20.html";
//	public static  String str_url = "http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html";//热点
//	public static  String url2 = "http://c.3g.163.com/nc/video/list/V9LG4CHOR/n/10-10.html";//娱乐
//	public static  String url3 = "http://c.3g.163.com/nc/video/list/V9LG4E6VR/n/10-10.html";//搞笑
//	public static  String url4 = "http://c.3g.163.com/nc/video/list/00850FRB/n/10-10.html";//精选
	
	
	public static String str_getContent = "准备接受内容...";
	public static String str_notice = " ";
	public static String str_path = "/mnt/sdcard/fjw_work/";
	public static String str_file = "myNetFile.html";
	SaveFileThread saveFileThread; 
	HttpThread httpThread;
	public static Context myContext;

	
	private OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0==Bn_Exit){
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory("android.intent.category.HOME");
				startActivity(intent);
			}else if(arg0==Bn_Next){
				Intent intent = new Intent(MainActivity.this, JsonActivity.class);
				startActivity(intent);
			}else if(arg0==Bn_Request){
				str_getContent = "";  //清空 之前获取数据
				String url = Ed_Url.getText().toString();
				if("".equals(url)==false){
					str_url = url;
				}				
				//请求 http 网络数据
				httpThread = new HttpThread(myHandler,str_url);
				httpThread.start();				

			}else if(arg0==Bn_CreateFile){
				String path = Ed_path.getText().toString();
				if("".equals(path)==false)  str_path = path;
				String file = Ed_fileName.getText().toString();
				if("".equals(file)==false)  str_file = file;
				//生成本地文件
				saveFileThread = new SaveFileThread(myHandler,str_path+str_file,str_getContent);
				saveFileThread.start();
			}
		}
	};
	
	
	private Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 1:   //刷新控件 
				str_getContent = httpThread.get_str;
				if(str_getContent != null){
					Tx_GetContent.setText(str_getContent);    //刷新 Tx_GetContent
			//		Tx_Notice.setText(str_notice);            //刷新 Tx_Notice
				}
				break;
			case 2:   //文件生成  Dialog 
				new AlertDialog.Builder(MainActivity.this)
				    .setTitle("提示")
				    .setMessage("网络请求接收文件保存成功！")
				    .show();
				break;
			case 3: //文件生成失败  Dialog 
				new AlertDialog.Builder(MainActivity.this)
			    .setTitle("提示")
			    .setMessage("网络请求接收文件保存失败  Error！")
			    .show();
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
		setContentView(R.layout.activity_main); 
		myContext = this;
		
		Bn_Exit = (Button)findViewById(R.id.Bn_Exit); 
		Bn_Next = (Button)findViewById(R.id.Bn_Next); 
		Ed_Url = (EditText)findViewById(R.id.Ed_Url); 
		Bn_Request = (Button)findViewById(R.id.Bn_request);
		Tx_GetContent = (TextView)findViewById(R.id.Tx_getContent);
//		Tx_Notice = (TextView)findViewById(R.id.Tx_notice);
		Ed_path = (EditText)findViewById(R.id.Ed_PathName);
		Ed_fileName = (EditText)findViewById(R.id.Ed_fileName);
		Bn_CreateFile = (Button)findViewById(R.id.Bn_CreateFile);
		
		Bn_Exit.setOnClickListener(l);  
		Bn_Next.setOnClickListener(l); 
		Bn_Request.setOnClickListener(l);  
		Bn_CreateFile.setOnClickListener(l);
		
		Ed_Url.setHint(str_url);
		Ed_path.setHint(str_path);
		Ed_fileName.setHint(str_file);
	}
	

}
