package Myhttp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import myapplication.MyApplication;
import android.content.Intent;
//import android.os.Looper;
//import MyApplicationInit.MyApplication;
import android.util.Log;



/**
 * HttpURLConnection  �ӿ�       java.net ����httpͨ��  �̳�URLConnection
 * androidԴ����Ŀ
 * */
public class HttpURLConnectionHAL {

	public HttpURLConnectionHAL() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ֻ������ GET POST
	 * һ���ʹ������
	 * post:  http://xxx:8081//servlet/LoginServlt
	 * get:   http://xxx:8081//servlet/LoginServlt?username=root&pwd=123
	 * 
	 * HttpURLConnection �Ƚ� URLConnection 
	 * ǰ�� �̳��ں��� ������ǰ�� ֻ����ר�Ŵ���http����   ����ʹ������http���з��� setRequestMethod(Get/Post)
	 * 
	 * �������ԣ����ֱ�ӷ���InputStream���������ر������� ���е��û�ȡ�������ֽڵ�bug (������ر������ǿ��Ի�ȡ��)
	 * �ʣ��޸�Ϊ�����ֽ�����byte[].  ����new ByteArrayOutputStream ��ȡ��δ֪��С��byte���顣
	 * */
	
	//���� GET ����  ��ȡ�ַ���
	public static String sendReques_Get(String strUrl){
		String str_response = "";
		HttpURLConnection connection = null;
		try{
			URL url = new URL(strUrl);
			connection = (HttpURLConnection)url.openConnection();   //����������   ���¿���ֱ���շ�������
			connection.setRequestMethod("GET"); //����Ϊ�����ȡ����     ���͡�GET��
			//connection.setRequestProperty("encoding", "uft-8");  ָ������
			connection.setConnectTimeout(5000); //�����������ӳ�ʱ   ���Բ�����
			connection.setReadTimeout(5000);    //���������ȡ��ʱ   ���Բ�����		
			if(connection.getResponseCode() == 200){  //���ݽ��ճɹ�     HTTP_OK=200
				InputStream in = connection.getInputStream(); //��ȡ�����ֽ���
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
				String line;
				while((line = bufferReader.readLine())!=null){
			//		Log.v("sendReques_Get>>"," "+line);
					str_response = str_response + line + "\n";
				}
			}	
		}catch(Exception e){
			Log.e("HttpURLConnectionHAL->sendReques_Get","���������쳣�׳���");
		//	Looper.prepare(); //���� ���̵߳�looper��message.  Toast�����߳� ��Ҫ��Looper
		//	Toast.makeText(MyApplication.getContextObject(), "HttpURLConnectionHAL-Get Error!", Toast.LENGTH_LONG).show();
		//	Looper.loop();  //looper ָ���ƶ� ѭ������   ��  ���̸߳��õ��������     ������ʹ��   �ᵼ�²��ϲ������߳�  
			Intent intent = new Intent("Fang.MyBroadcast.Error");
			intent.putExtra("fang", "HttpURLConnectionHAL-GET �ַ� �����쳣");
			MyApplication.getContextObject().sendBroadcast(intent);
		}finally{
			if (connection != null) { 
				connection.disconnect();  
				} 
		}
		return str_response;
	}
	
	//���� GET ����    ͨ������ �������ر����� ���Բ���ֱ�ӷ���InputStream ���÷���byte[]
	public static byte[] getBytes_Get(String strUrl){
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		InputStream in = null;
		HttpURLConnection connection = null;
		try{
			URL url = new URL(strUrl);
			connection = (HttpURLConnection)url.openConnection();   //����������   ���¿���ֱ���շ�������
			connection.setRequestMethod("GET"); //����Ϊ�����ȡ����     ���͡�GET��
			//connection.setRequestProperty("encoding", "uft-8");  ָ������
			connection.setConnectTimeout(5000); //�����������ӳ�ʱ   ���Բ�����
			connection.setReadTimeout(5000);    //���������ȡ��ʱ   ���Բ�����		
			if(connection.getResponseCode() == 200){  //���ݽ��ճɹ�
			    in = connection.getInputStream(); //��ȡ�����ֽ���
			  //���ֽ��� д�뵽 ����buf
			    byte[] buffer = new byte[1024];
			    int count = 0;
			    while( (count=in.read(buffer)) != -1){
			    	byteOutputStream.write(buffer,0,count);
			    }
                in.close();
                byteOutputStream.close();
			}	
		}catch(Exception e){
			Log.e("HttpURLConnectionHAL->getInputStream_Get","���������쳣�׳���");
			Intent intent = new Intent("Fang.MyBroadcast.Error");
			intent.putExtra("fang", "HttpURLConnectionHAL-GET �ֽ��� �����쳣");
			MyApplication.getContextObject().sendBroadcast(intent);
		}finally{
			if (connection != null) { 
				connection.disconnect();  
				} 
		}
		return byteOutputStream.toByteArray();
	}
	
	//���� POST ����   ��ȡ�ַ�   δ����
	public static String sendReques_Post(String strUrl, String para){
		String str_response = "";
		HttpURLConnection connection = null;
		try{
			URL url = new URL(strUrl);
			connection = (HttpURLConnection)url.openConnection();   //����������   ���¿���ֱ���շ�������
			connection.setRequestMethod("POST"); //����Ϊ�����ȡ����     ���͡�GET��
		//	connection.setRequestProperty("contentType", "");  //���ô��������
		//	connection.setRequestProperty("Content-Length", String.valueOf(para.getBytes().length));  //���ô�������ݳ���
			connection.setDoInput(true);  //���� ��ȡ����
			connection.setDoOutput(true);  //���� д������
			connection.setConnectTimeout(3000); //�����������ӳ�ʱ   ���Բ�����
			connection.setReadTimeout(5000);    //���������ȡ��ʱ   ���Բ�����		
			
			//�� ����  
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes("username=admin&password=123456");
			//�� ����
			if(connection.getResponseCode() == 200){  //���ݽ��ճɹ�
				InputStream in = connection.getInputStream(); //��ȡ�����ֽ���
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
				String line;
				while((line = bufferReader.readLine())!=null){
					str_response = str_response + line + "\n";
				}
			}	
		}catch(Exception e){
			Intent intent = new Intent("Fang.MyBroadcast.Error");
			intent.putExtra("fang", "HttpURLConnectionHAL-POST ��ȡ�ַ� �����쳣");
			MyApplication.getContextObject().sendBroadcast(intent);		
		}finally{
			if (connection != null) {
				connection.disconnect();
				}
		}
		return str_response;
	}
	
	//���� POST ����   ��ȡ�ֽ�����   ����--notice:bug��Ҫ������ ���ܹر�����
	public static byte[] getBytes_Post(String strUrl, String para){
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		InputStream in = null;
		HttpURLConnection connection = null;
		try{
			URL url = new URL(strUrl);
			connection = (HttpURLConnection)url.openConnection();   //����������   ���¿���ֱ���շ�������
			connection.setRequestMethod("POST"); //����Ϊ�����ȡ����     ���͡�GET��
		//	connection.setRequestProperty("contentType", "");  //���ô��������
		//	connection.setRequestProperty("Content-Length", String.valueOf(para.getBytes().length));  //���ô�������ݳ���
			connection.setDoInput(true);  //���� ��ȡ����
			connection.setDoOutput(true);  //���� д������
			connection.setConnectTimeout(3000); //�����������ӳ�ʱ   ���Բ�����
			connection.setReadTimeout(5000);    //���������ȡ��ʱ   ���Բ�����		
			
			//�� ����  
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes("username=admin&password=123456");
			//�� ����
			if(connection.getResponseCode() == 200){  //���ݽ��ճɹ�
				in = connection.getInputStream(); //��ȡ�����ֽ���
				//���ֽ��� д�뵽 ����buf
				 byte[] buffer = new byte[1024];
				 int count = 0;
				 while( (count=in.read(buffer)) != -1){
				    	byteOutputStream.write(buffer,0,count);
				 }
	             in.close();
	             byteOutputStream.close();
			}	
		}catch(Exception e){
			Intent intent = new Intent("Fang.MyBroadcast.Error");
			intent.putExtra("fang", "HttpURLConnectionHAL-POST ��ȡ�ֽ��� �����쳣");
			MyApplication.getContextObject().sendBroadcast(intent);		
		}finally{
			if (connection != null) {
				connection.disconnect(); //�ر������Ӷ� ���ص��ֽ�����Ӱ��
			}
		}
		return byteOutputStream.toByteArray();
	}

}
