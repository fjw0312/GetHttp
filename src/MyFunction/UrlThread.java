package MyFunction;

import java.io.InputStream;

import myapplication.MyApplication;

import Myhttp.HttpURLConnectionHAL;
import Myhttp.URLConnectionHAL;
import Myhttp.UrlHAL;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

public class UrlThread extends Thread{

	public UrlThread(String url, Handler handler) {
		// TODO Auto-generated constructor stub
		str_url = url;
		myHandler = handler;
	}
	
	public String str_url = "";
	private Handler myHandler;
	
	public InputStream in;
	public Bitmap bitmap;
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		//url 请求 数据流 
		byte[] bs = HttpURLConnectionHAL.getBytes_Get(str_url);
		bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
		
	//	in = UrlHAL.getInputStream_URL(str_url);            //测试也是OK 的 可以下载图片文件
	//	in = URLConnectionHAL.getInputStream_URL(str_url);  //测试也是OK 的 可以下载图片文件		
    //  bitmap = BitmapFactory.decodeStream(in);
		myHandler.sendEmptyMessage(1); //发送 网络请求 结束消息 
		
	}

}
