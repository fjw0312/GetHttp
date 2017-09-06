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
 * HttpURLConnection  接口       java.net 网络http通信  继承URLConnection
 * android源生项目
 * */
public class HttpURLConnectionHAL {

	public HttpURLConnectionHAL() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 只适用于 GET POST
	 * 一般的使用区别
	 * post:  http://xxx:8081//servlet/LoginServlt
	 * get:   http://xxx:8081//servlet/LoginServlt?username=root&pwd=123
	 * 
	 * HttpURLConnection 比较 URLConnection 
	 * 前者 继承于后者 ，所以前者 只适用专门处理http请求   可以使用设置http特有方法 setRequestMethod(Get/Post)
	 * 
	 * 进过测试，如果直接返回InputStream，由于最后关闭了连接 会有调用获取不到流字节的bug (如果不关闭连接是可以获取到)
	 * 故，修改为返回字节数组byte[].  借助new ByteArrayOutputStream 获取到未知大小的byte数组。
	 * */
	
	//发送 GET 请求  获取字符流
	public static String sendReques_Get(String strUrl){
		String str_response = "";
		HttpURLConnection connection = null;
		try{
			URL url = new URL(strUrl);
			connection = (HttpURLConnection)url.openConnection();   //与服务端连接   接下可以直接收发数据了
			connection.setRequestMethod("GET"); //设置为请求获取数据     发送“GET”
			//connection.setRequestProperty("encoding", "uft-8");  指定编码
			connection.setConnectTimeout(5000); //设置网络连接超时   可以不设置
			connection.setReadTimeout(5000);    //设置网络读取超时   可以不设置		
			if(connection.getResponseCode() == 200){  //数据接收成功     HTTP_OK=200
				InputStream in = connection.getInputStream(); //获取网络字节流
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
				String line;
				while((line = bufferReader.readLine())!=null){
			//		Log.v("sendReques_Get>>"," "+line);
					str_response = str_response + line + "\n";
				}
			}	
		}catch(Exception e){
			Log.e("HttpURLConnectionHAL->sendReques_Get","网络请求异常抛出！");
		//	Looper.prepare(); //创建 该线程的looper和message.  Toast所在线程 需要有Looper
		//	Toast.makeText(MyApplication.getContextObject(), "HttpURLConnectionHAL-Get Error!", Toast.LENGTH_LONG).show();
		//	Looper.loop();  //looper 指针移动 循环阻塞   故  在线程复用调用情况下     不建议使用   会导致不断产生新线程  
			Intent intent = new Intent("Fang.MyBroadcast.Error");
			intent.putExtra("fang", "HttpURLConnectionHAL-GET 字符 网络异常");
			MyApplication.getContextObject().sendBroadcast(intent);
		}finally{
			if (connection != null) { 
				connection.disconnect();  
				} 
		}
		return str_response;
	}
	
	//发送 GET 请求    通过测试 由于最后关闭连接 所以不能直接返回InputStream 故用返回byte[]
	public static byte[] getBytes_Get(String strUrl){
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		InputStream in = null;
		HttpURLConnection connection = null;
		try{
			URL url = new URL(strUrl);
			connection = (HttpURLConnection)url.openConnection();   //与服务端连接   接下可以直接收发数据了
			connection.setRequestMethod("GET"); //设置为请求获取数据     发送“GET”
			//connection.setRequestProperty("encoding", "uft-8");  指定编码
			connection.setConnectTimeout(5000); //设置网络连接超时   可以不设置
			connection.setReadTimeout(5000);    //设置网络读取超时   可以不设置		
			if(connection.getResponseCode() == 200){  //数据接收成功
			    in = connection.getInputStream(); //获取网络字节流
			  //将字节流 写入到 数组buf
			    byte[] buffer = new byte[1024];
			    int count = 0;
			    while( (count=in.read(buffer)) != -1){
			    	byteOutputStream.write(buffer,0,count);
			    }
                in.close();
                byteOutputStream.close();
			}	
		}catch(Exception e){
			Log.e("HttpURLConnectionHAL->getInputStream_Get","网络请求异常抛出！");
			Intent intent = new Intent("Fang.MyBroadcast.Error");
			intent.putExtra("fang", "HttpURLConnectionHAL-GET 字节流 网络异常");
			MyApplication.getContextObject().sendBroadcast(intent);
		}finally{
			if (connection != null) { 
				connection.disconnect();  
				} 
		}
		return byteOutputStream.toByteArray();
	}
	
	//发送 POST 请求   获取字符   未测试
	public static String sendReques_Post(String strUrl, String para){
		String str_response = "";
		HttpURLConnection connection = null;
		try{
			URL url = new URL(strUrl);
			connection = (HttpURLConnection)url.openConnection();   //与服务端连接   接下可以直接收发数据了
			connection.setRequestMethod("POST"); //设置为请求获取数据     发送“GET”
		//	connection.setRequestProperty("contentType", "");  //设置传入的类型
		//	connection.setRequestProperty("Content-Length", String.valueOf(para.getBytes().length));  //设置传入的数据长度
			connection.setDoInput(true);  //允许 读取数据
			connection.setDoOutput(true);  //允许 写入数据
			connection.setConnectTimeout(3000); //设置网络连接超时   可以不设置
			connection.setReadTimeout(5000);    //设置网络读取超时   可以不设置		
			
			//先 发送  
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes("username=admin&password=123456");
			//再 接收
			if(connection.getResponseCode() == 200){  //数据接收成功
				InputStream in = connection.getInputStream(); //获取网络字节流
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
				String line;
				while((line = bufferReader.readLine())!=null){
					str_response = str_response + line + "\n";
				}
			}	
		}catch(Exception e){
			Intent intent = new Intent("Fang.MyBroadcast.Error");
			intent.putExtra("fang", "HttpURLConnectionHAL-POST 获取字符 网络异常");
			MyApplication.getContextObject().sendBroadcast(intent);		
		}finally{
			if (connection != null) {
				connection.disconnect();
				}
		}
		return str_response;
	}
	
	//发送 POST 请求   获取字节数组   测试--notice:bug需要返回流 不能关闭连接
	public static byte[] getBytes_Post(String strUrl, String para){
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		InputStream in = null;
		HttpURLConnection connection = null;
		try{
			URL url = new URL(strUrl);
			connection = (HttpURLConnection)url.openConnection();   //与服务端连接   接下可以直接收发数据了
			connection.setRequestMethod("POST"); //设置为请求获取数据     发送“GET”
		//	connection.setRequestProperty("contentType", "");  //设置传入的类型
		//	connection.setRequestProperty("Content-Length", String.valueOf(para.getBytes().length));  //设置传入的数据长度
			connection.setDoInput(true);  //允许 读取数据
			connection.setDoOutput(true);  //允许 写入数据
			connection.setConnectTimeout(3000); //设置网络连接超时   可以不设置
			connection.setReadTimeout(5000);    //设置网络读取超时   可以不设置		
			
			//先 发送  
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes("username=admin&password=123456");
			//再 接收
			if(connection.getResponseCode() == 200){  //数据接收成功
				in = connection.getInputStream(); //获取网络字节流
				//将字节流 写入到 数组buf
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
			intent.putExtra("fang", "HttpURLConnectionHAL-POST 获取字节流 网络异常");
			MyApplication.getContextObject().sendBroadcast(intent);		
		}finally{
			if (connection != null) {
				connection.disconnect(); //关闭了连接对 返回的字节流有影响
			}
		}
		return byteOutputStream.toByteArray();
	}

}
