package MyFunction;

import android.os.Handler;
import Myfile.FileHAL;


/***
 * 文件生成保存 线程类
 * 
 * */
public class SaveFileThread extends Thread{
	public SaveFileThread(Handler handler,String FileName,String getStr) {
		// TODO Auto-generated constructor stub
		myHandler = handler;
		str_FileName = FileName;
		str_getContent = getStr;
	}
	
	public String str_FileName = "";
	public String str_getContent = "";
	
	private Handler myHandler;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		FileHAL fileHAL = new FileHAL(str_FileName);
		boolean bt = fileHAL.write_str(str_getContent);
		if(bt){
			myHandler.sendEmptyMessage(2);  //发送文件生成 成功消息
		}else{
			myHandler.sendEmptyMessage(3);  //发送文件生成 失败消息
		}
	}

}
