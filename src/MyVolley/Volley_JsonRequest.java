package MyVolley;

import java.util.ArrayList;
import java.util.List;

import myapplication.MyApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gethttp.JsonActivity;

public class Volley_JsonRequest {

	public Volley_JsonRequest(JsonActivity jsonActivity) {
		// TODO Auto-generated constructor stub
		myActivity = jsonActivity;
	}
	
	JsonActivity myActivity;
	String typeId = "";
	String dataId = "";
	
	public  void getArrayJson_Volley_Get(String strUrl, String para1, String para2){	
		typeId = para1;
		dataId = para2;
		
		//1.创建 RequestQueue
		RequestQueue mQueue = Volley.newRequestQueue(myActivity);
		//2.创建StringRequest
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(strUrl, null, 
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						List<String> newsModel_lst = new ArrayList<String>();
						try {
							JSONObject jObject = response.getJSONObject(typeId);
							JSONArray jsonArray = jObject.getJSONArray(dataId);
							if(jsonArray == null)  return;
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jsonObject = jsonArray.getJSONObject(i);
						
								String id =  String.valueOf(i);
								String uniquekey = jsonObject.getString("uniquekey");
								String title = jsonObject.getString("title");
								String date = jsonObject.getString("date");
								String category = jsonObject.getString("category");
								String author_name = jsonObject.getString("author_name");
								String url = jsonObject.getString("url");
								String thumbnail_pic_s = jsonObject.getString("thumbnail_pic_s");
								//String thumbnail_pic_s02 = jsonObject.getString("thumbnail_pic_s02"); //不一定有 会出错抛出
								//String thumbnail_pic_s03 = jsonObject.getString("thumbnail_pic_s03");
								
								String str = new String();
								str = title+"\n"+date+"\n"+category+"\n"+author_name+"\n"+url+"\n" +thumbnail_pic_s;
								
								newsModel_lst.add(str);   				
							}
				
							//处理 获取到的数据链表   业务
							myActivity.initAdapter(newsModel_lst);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Intent intent = new Intent("Fang.MyBroadcast.Error");
							intent.putExtra("fang", "volley获取网络新闻返回数据 解析出错！");
							MyApplication.getContextObject().sendBroadcast(intent);
						} 
					}
				}, 
                new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Intent intent = new Intent("Fang.MyBroadcast.Error");
						intent.putExtra("fang", "getArrayJson_Volley_Get  网络异常");
						MyApplication.getContextObject().sendBroadcast(intent);
					}
				});
		//3. StringRequest 添加到Queue
		mQueue.add(jsonObjectRequest);
	}

}
