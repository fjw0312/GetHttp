package MyFunction;

import java.io.IOException;
import java.io.Reader;

import org.json.JSONObject;

import com.example.gethttp.MainActivity;

import myapplication.MyApplication;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import MyVolley.VolleyHAL;
import Myhttp.HttpClientHAL;
import Myhttp.HttpURLConnectionHAL;
import Myhttp.URLConnectionHAL;


/***
 * ����  http �����߳� ��
 *  
 *  
 * */
public class HttpThread extends Thread{
	public HttpThread(Handler handler, String url) {
		// TODO Auto-generated constructor stub
		myHandler = handler;
		str_url = url;
	}
	
	public String str_url = "";
	public String get_str = "";
	private Handler myHandler;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		get_str = HttpURLConnectionHAL.sendReques_Get(str_url);  //����  Ok
	//	get_str = HttpClientHAL.sendReques_Get(str_url);         //����  Ok
	//	get_str = VolleyHAL.getString_Volley_Get(MyApplication.getContextObject(), str_url);  //���� Ok
	//	get_str = VolleyHAL.getJsonObject_Volley_Get(MainActivity.myContext, str_url).toString(); //�����״�������{}	
		
		myHandler.sendEmptyMessage(1); //���� �������� ������Ϣ 
		
	}



}
