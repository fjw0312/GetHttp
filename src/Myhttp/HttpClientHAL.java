package Myhttp;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Queue;

import myapplication.MyApplication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.gethttp.MainActivity;


//import MyApplicationInit.MyApplication;
import android.content.Intent;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;


/**
 * HttpClient �ӿ�
 * android ��Ƕ��apach ��ԴHttpClient  ��android 6.0 �ͱ������ˡ�
 * date:2017.5.24
 * */
public class HttpClientHAL { 

	public HttpClientHAL() {
		// TODO Auto-generated constructor stub
	}
	
	//����  GET ����
	public static String sendReques_Get(String strUrl){
		String str_response = "";
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strUrl);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			//���շ�����Ӧ
			if(httpResponse.getStatusLine().getStatusCode()==200){  //HTTP_OK=200	
				HttpEntity httpEntity = httpResponse.getEntity();			
				str_response = EntityUtils.toString(httpEntity,"utf-8");		
			}
		}catch(Exception e){
			Log.e("HttpClientHAL->sendReques_Get","�����쳣�׳���");
			//����  �㲥֪ͨ
			Intent intent = new Intent("Fang.MyBroadcast.Error");
			intent.putExtra("fang", "HttpClientHAL-GET �����쳣");
			MyApplication.getContextObject().sendBroadcast(intent);	   
		}
		return str_response;
	}
	
	//�ϱ�    POST
	public static String sendReques_Post(String strUrl){
		String str_response = "";
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(strUrl);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", "admin"));
			params.add(new BasicNameValuePair("password", "123456"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
			httpPost.setEntity(entity);
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			//���շ�����Ӧ
			if(httpResponse.getStatusLine().getStatusCode()==200){
				HttpEntity httpEntity = httpResponse.getEntity();	
				str_response = EntityUtils.toString(httpEntity,"utf-8");
			}
		}catch(Exception e){
			Log.e("HttpClientHAL->sendReques_Get","�����쳣�׳���");
			//����  �㲥֪ͨ
			Intent intent = new Intent("Fang.MyBroadcast.Error");
			intent.putExtra("fang", "HttpClientHAL-GET �����쳣");
			MyApplication.getContextObject().sendBroadcast(intent);	 
		}
		return str_response;
	}
}