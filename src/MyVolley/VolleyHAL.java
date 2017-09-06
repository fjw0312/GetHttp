package MyVolley;

import org.json.JSONObject;

import myapplication.MyApplication;
import android.content.Context;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gethttp.JsonActivity;


/***
 * 使用 网络开源框架 Volley 请求网络数据
 * author：fjw0312
 * date:2017.7.27
 * 
 * 目前发现  Listener  ErrorListener 应该和RequestQueue线程异步的。
 * 所以应该 不能直接RequestQueue后直接返回Listener 获得的数据
 * 
 * */
public class VolleyHAL {

	public VolleyHAL() {
		// TODO Auto-generated constructor stub
	}
	
	public static String strRe = "";
	public static JSONObject jsonObject = new JSONObject();
	
	public static String getString_Volley_Get(Context context,String strUrl){
		
		//1.创建 RequestQueue
		RequestQueue mQueue = Volley.newRequestQueue(context);
		//2.创建StringRequest
		StringRequest stringRequest = new StringRequest(strUrl, 
				new Listener<String>() { //重写  volley.listener.onResponse
					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						strRe = response;
					}
				}, 
                new ErrorListener() {//重写  volley.onErrorResponse.onResponse
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Intent intent = new Intent("Fang.MyBroadcast.Error");
						intent.putExtra("fang", "getString_Volley_Get  网络异常");
						MyApplication.getContextObject().sendBroadcast(intent);
					}
				});
		//3. StringRequest 添加到Queue
		mQueue.add(stringRequest);
		
		return strRe;
	}	
	
	public static JSONObject getJsonObject_Volley_Get(Context context,String strUrl){

		//1.创建 RequestQueue
		RequestQueue mQueue = Volley.newRequestQueue(context);
		//2.创建StringRequest
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(strUrl, null, 
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						jsonObject = response;
					}
				}, 
                new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Intent intent = new Intent("Fang.MyBroadcast.Error");
						intent.putExtra("fang", "getJsonObject_Volley_Get  网络异常");
						MyApplication.getContextObject().sendBroadcast(intent);
					}
				});
		//3. StringRequest 添加到Queue
		mQueue.add(jsonObjectRequest);
		
		return jsonObject;
	}

}
