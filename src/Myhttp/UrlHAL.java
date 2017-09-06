package Myhttp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * URL  接口      获取统一资源定位器
 * UrlHAL  只能读取url资源
 * 返回：字节流   字符流   对象类
 * 该接口还不能直接请求 Get Post 请求需要进一步封装
 * */
public class UrlHAL {

	public UrlHAL() {
		// TODO Auto-generated constructor stub
	}
	//解析 统一资源定位符   
	public static String urlParse(String strUrl){
		String str = strUrl+"/n";
		try {
			URL url = new URL(strUrl);
			str = str +"协议："+ url.getProtocol() +"\n";
			str = str +"验证信息："+ url.getAuthority() +"\n";
			str = str +"文件名及请求参数："+ url.getFile() +"\n";
			str = str +"主机名："+ url.getHost() +"\n";
			str = str +"路劲："+ url.getPath() +"\n";
			str = str +"端口："+ url.getPort() +"\n";
			str = str +"默认端口："+ url.getDefaultPort() +"\n";
			str = str +"请求参数："+ url.getQuery() +"\n";
			str = str +"定位位置："+ url.getRef() +"\n";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}           //实例化URL
		return str;
	}
	//下载网络文件到本地
	public static void downLoadFormUrl(String strUrl,String savePath, String fileName) throws IOException{
		URL url = new URL(strUrl);           //实例化URL 
		DataInputStream in = new DataInputStream(url.openStream()); 
		DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath+fileName));
		byte[] buffer = new byte[2048];
		int count = 0;
		while( (count = in.read(buffer,0,2048))>0 ){
			out.write(buffer, 0, count);
		}
		out.close();
		in.close();
	}
	
	//获取URL 定位资源（包括网络）    返回资源   字节流
	public static InputStream getInputStream_URL(String strUrl){
		InputStream in = null;
		try {
			URL url = new URL(strUrl);           //实例化URL
			in = url.openStream();   //打开资源 获取到输入流		
			
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}
	//获取URL 定位资源（包括网络）    返回资源   字符流
	public static Reader getReader_URL(String strUrl){
		    Reader in = null;
			try {
				URL url = new URL(strUrl);           //实例化URL
				InputStream inStream = url.openStream();   //打开资源 获取到输入流		
				in = new InputStreamReader(inStream);   //打开资源 获取到输入流	
				//or/ in = new InputStreamReader(new BufferedInputStream(url.openStream()));
				
			}catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return in;
		}
	//获取URL 定位资源（包括网络）    返回资源   对象
	public static Object getObject_URL(String strUrl){
		    Object object = null;
			try {
				URL url = new URL(strUrl);           //实例化URL
				object = url.getContent();				
				
			}catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return object;
		}
}
