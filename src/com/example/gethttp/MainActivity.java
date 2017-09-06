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
 * Ŀǰ��Ҫ�Ż������⣺
 * 1.�����쳣 Ӧ��  Toast֪ͨ
 * 2.���緵��string ���й�������⡣
 * 
 * 2017.7.20 dubug:����activity ģʽ�޸�ΪsingleTask 
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
//	public static  String str_url = "http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html";//�ȵ�
//	public static  String url2 = "http://c.3g.163.com/nc/video/list/V9LG4CHOR/n/10-10.html";//����
//	public static  String url3 = "http://c.3g.163.com/nc/video/list/V9LG4E6VR/n/10-10.html";//��Ц
//	public static  String url4 = "http://c.3g.163.com/nc/video/list/00850FRB/n/10-10.html";//��ѡ
	
	
	public static String str_getContent = "׼����������...";
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
				str_getContent = "";  //��� ֮ǰ��ȡ����
				String url = Ed_Url.getText().toString();
				if("".equals(url)==false){
					str_url = url;
				}				
				//���� http ��������
				httpThread = new HttpThread(myHandler,str_url);
				httpThread.start();				

			}else if(arg0==Bn_CreateFile){
				String path = Ed_path.getText().toString();
				if("".equals(path)==false)  str_path = path;
				String file = Ed_fileName.getText().toString();
				if("".equals(file)==false)  str_file = file;
				//���ɱ����ļ�
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
			case 1:   //ˢ�¿ؼ� 
				str_getContent = httpThread.get_str;
				if(str_getContent != null){
					Tx_GetContent.setText(str_getContent);    //ˢ�� Tx_GetContent
			//		Tx_Notice.setText(str_notice);            //ˢ�� Tx_Notice
				}
				break;
			case 2:   //�ļ�����  Dialog 
				new AlertDialog.Builder(MainActivity.this)
				    .setTitle("��ʾ")
				    .setMessage("������������ļ�����ɹ���")
				    .show();
				break;
			case 3: //�ļ�����ʧ��  Dialog 
				new AlertDialog.Builder(MainActivity.this)
			    .setTitle("��ʾ")
			    .setMessage("������������ļ�����ʧ��  Error��")
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
		requestWindowFeature(Window.FEATURE_NO_TITLE); //���س�app������ ���setContentView֮ǰ 
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
