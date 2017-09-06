package Myfile;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

/***
 * �ļ����� �ӿ�
 * 
 * */
public class FileHAL {
	
	File file = null;   //�ļ������
	Writer fws = null;   //д���ַ���
	Reader frs = null;   //�����ַ���   
	InputStream fis = null;  //��ȡ�ֽ���
	OutputStream fos = null; //д���ֽ���
	BufferedReader bufread = null; //�߼���ȡ�ı���
	PrintWriter  priwrite = null; //�߼�д���ı���
	
	public FileHAL() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FileHAL(String strFileName) {
		super();
		// TODO Auto-generated constructor stub
		file = new File(strFileName);
		if(!file.exists()){ //�ж��ļ�/Ŀ¼�Ƿ���� �������½�
			try{
			file.createNewFile(); //�½�Ŀ¼/�ļ�
			}catch(IOException e){
				Log.e("�ļ�","����ʧ��");
			}
		}
		//�ж��ļ��Ƿ�ɶ�
		if(!file.canRead()){
			Log.e("�ļ�","���ɶ�");
		}	
		
		//�ж��ļ��Ƿ��д
		if(!file.canWrite()){
			Log.e("�ļ�","����д");
		}
	}
	
	//---------------------------HAL---------------------------//
	//�����ֽ�
	public byte[] read_byte(byte[] buf){
			
		try{		
			fis = new FileInputStream(file);
			for(int i=0; (fis.read(buf,0,buf.length))!=-1;i++);
			fis.close();
			}catch(Exception e){
				Log.e("�ļ�","�����ֽ���ʧ�ܣ�");
				return null;
			}
				
			return buf;
	}	
	//д�����ֽ�
	public boolean write_byte(byte[] buf){
			
		try{
			fos = new FileOutputStream(file);		
			fos.write(buf);
			fos.close();
			}catch(Exception e){
				Log.e("�ļ�","д���ֽ���ʧ�ܣ�");
				return false;
			}
		
			return true;
	}

	//�����ַ���
	public String read_buf(String buf){
		
		try{
			char c[] = new char[1024]; //��ȡ���1024��
			frs = new FileReader(file);
			for(int i=0; (frs.read(c))!=-1;i++);
			frs.close();
			buf = c.toString();
			}catch(Exception e){
				Log.e("�ļ�","�����ַ���ʧ�ܣ�");
			}
		
		return buf;
	}
	//д���ַ���
	public boolean write_buf(String buf){
		
		try{
		fws = new FileWriter(file);
		fws.write(buf);
		fws.close();
		}catch(Exception e){
			Log.e("�ļ�","д���ַ���ʧ�ܣ�");
			return false;
		}
		
		return true;
	}
	//=================================ʹ�ø߼���  ����=========================================
	//*************************д�벿��
	//�߼�������  β��д�� �����ַ���
	public boolean write_str(String buf){

		try{
			priwrite = new PrintWriter(new FileWriter(file,true),true); //trueβ��д�����ַ���
			priwrite.print(buf);  //β��д�����ַ���
			priwrite.close();
			}catch(Exception e){
				Log.e("�ļ�","д���ַ���ʧ�ܣ�");
				return false;
			}
		
			return true;
	}
	//�߼�������  д���ַ��� �����ַ���
	public boolean write_line(String buf){

		try{
			priwrite = new PrintWriter(new FileWriter(file,true),true); //trueβ��д�����ַ���
			priwrite.println(buf);  //β��д�����ַ���
			priwrite.close();
			}catch(Exception e){
				Log.e("�ļ�","д���ַ���ʧ�ܣ�");
				return false;
			}
		
			return true;
	}
	//�߼�������   д����� �ַ���    �����ַ���
	public boolean write_lines(String[] bufs){
		if(bufs==null) return false;
		
		try{
			priwrite = new PrintWriter(new FileWriter(file,true),true); //trueβ��д�����ַ���
			for(int i=0;i<bufs.length;i++){
				priwrite.println(bufs[i]);  //β��д�����ַ���
			}
			priwrite.close();
			}catch(Exception e){
				Log.e("�ļ�","д���ַ���ʧ�ܣ�");
				return false;
			}
		
			return true;
	}
	//***********************��ȡ����
	//������  ͷһ���ַ� �����ַ���
	public String read_first_line(){				
		String buf_line = "";
		try{
			bufread = new BufferedReader( new FileReader(file));
			if((buf_line = bufread.readLine())==null){
				bufread.close();
				return buf_line;
				}
			bufread.close();				
			}catch(Exception e){
				Log.e("�ļ�","�����ַ���ʧ�ܣ�");
			}
						
			return buf_line;
	}
	//���ļ�  �ļ����һ���ַ�
	public String read_later_line(){							
		String buf_later_line = "";
		try{
			 String line = "";
			 bufread = new BufferedReader( new FileReader(file));
			 while((line = bufread.readLine())!=null){	
				 buf_later_line = line;
			  }
			// Log.i("local_file->read_later_line��ȡ�������һ���ַ���",buf_later_line);
			  bufread.close();
			}catch(Exception e){
				Log.e("�ļ�","�����ַ���ʧ�ܣ�");
			}
						
			return buf_later_line;
	}
	//������  �������ַ� �����ַ�������
	public List<String> read_all_line(){
	
		String buf = new String();	
		List<String> buflist1 = new ArrayList<String>();
		buflist1.clear();
		try{			
			bufread = new BufferedReader( new FileReader(file));
			while((buf = bufread.readLine())!=null){
				buflist1.add(buf);
			}
			bufread.close();				
			}catch(Exception e){
				Log.e("�ļ�","�����ַ���ʧ�ܣ�");
			}
				
			return buflist1;
	}
	//��ȡ����ĳЩ�ַ���  ���ַ� �������ĳ�ַ���-�����ַ������������������ַ�
	public String search_str_line(String buf){
		String buf_str_line = "";
		String buff = "";
		try{
			 bufread = new BufferedReader( new FileReader(file));
			 while((buff = bufread.readLine())!=null){
		//		 if(buf_str_line.indexOf(buf)!= -1)  break;	 //����ĳ�ַ������˳�while
				 if(buff.contains(buf)){
					 buf_str_line = buff;
					 break;
				 }
			  }				 
			  bufread.close();
			}catch(Exception e){
				Log.e("�ļ�","�����ַ���ʧ�ܣ�");
			}
		return buf_str_line;
	}	
	//��ȡ����ĳЩ�ַ���  �������ַ� �������ĳ�ַ���-�����ַ�����ӽ�����������������������
	public List<String> search_str_all_line(String buf){
		String buff = "";
		List<String> buflist2 = new ArrayList<String>();
		buflist2.clear();
		try{
			 bufread = new BufferedReader( new FileReader(file));
			 while((buff = bufread.readLine())!=null){
		//		 if(buf_str_line.indexOf(buf)!= -1)  break;	 //����ĳ�ַ������˳�while
				 if(buff.contains(buf)){
					 buflist2.add(buff);
				 }
			  }				 
			  bufread.close();
			}catch(Exception e){
				Log.e("�ļ�","�����ַ���ʧ�ܣ�");
			}
		return buflist2;
	}
	
	//****************************�޸��ļ����� ����
	//�޸ĺ���ĳ�ַ��� �������ַ�   ������fileName�ļ����ư���·��  str��Ҫ����ַ����еı�ʶ�ַ�   newline������ַ��е�����
	public static boolean exchange_str_line(String fileName,String str, String newline){

		String buff = "";
		try{
			 File file1 = new File(fileName);
			 File file2 = new File(fileName+"txt");
			 file2.createNewFile(); //�½�Ŀ¼/�ļ�
			 
			 PrintWriter priwrite = new PrintWriter(new FileWriter(file2,true),true); //trueβ��д�����ַ��� 
			 BufferedReader bufread = new BufferedReader( new FileReader(file1));
			 while((buff = bufread.readLine())!=null){
				 if(buff.contains(str)){					 
					 buff = newline;
				 }
				 priwrite.println(buff);  //β��д�����ַ��� 
			  }				 
			  bufread.close();
			  priwrite.close();
			  file1.delete();
			  File file3 = new File(fileName);
			  file2.renameTo(file3);
			}catch(Exception e){
				Log.e("�ļ�","�����ַ���ʧ�ܣ�");
				return false;
			}
		return true;
	}

}
