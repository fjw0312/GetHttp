package MyFunction;

import android.os.Handler;
import Myfile.FileHAL;


/***
 * �ļ����ɱ��� �߳���
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
			myHandler.sendEmptyMessage(2);  //�����ļ����� �ɹ���Ϣ
		}else{
			myHandler.sendEmptyMessage(3);  //�����ļ����� ʧ����Ϣ
		}
	}

}
