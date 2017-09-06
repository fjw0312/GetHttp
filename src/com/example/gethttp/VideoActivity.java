package com.example.gethttp;

import java.net.URI;
import java.net.URL;

import MyFunction.UrlThread;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends Activity {

	Button Bn_Next;
	Button Bn_Per;
	EditText Ed_strUrl;
	VideoView Vd_video;
	Button Bn_Load;
	Button Bn_start;
	Button Bn_pause;
	Button Bn_stopPlayback;
	 

	MediaController mediaController;
	
	//public static String str_url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
	public static String str_url = "http://flv.bn.netease.com/videolib3/1411/24/Eqwbg0292/SD/movie_index.m3u8";
//	public static String str_url = "http://flv1.bn.netease.com/videolib3/1411/24/Eqwbg0292/SD/Eqwbg0292-mobile.mp4";
			
	private OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0==Bn_Next){
				Intent intent = new Intent(VideoActivity.this, MyVideoActivity.class);
				startActivity(intent);
			}else if(arg0==Bn_Per){
				Intent intent = new Intent(VideoActivity.this, NextActivity.class);
				startActivity(intent);
			}else if(arg0==Bn_Load){  //开始加载视频
				String str = Ed_strUrl.getText().toString();
				if("".equals(str)==false){ 
					str_url = str;				
				}
				init_videoView();
				Vd_video.setVideoURI( Uri.parse(str_url) );
				//Vd_video.setVideoPath(path);
				
			}else if(arg0==Bn_start){ //播放 视频
				Vd_video.start();	
			}else if(arg0==Bn_pause){
				Vd_video.pause();           //暂停播放
			}else if(arg0==Bn_stopPlayback){
				Vd_video.stopPlayback();    //停止播放	
			}
		}
	};
	
	
	//初始化 播放器
	private void init_videoView(){
		mediaController = new MediaController(VideoActivity.this);   //定义自动 添加播放控制器  （去掉也能播放）点击视频能看到播放条					
		Vd_video.setMediaController(mediaController);
		mediaController.setMediaPlayer(Vd_video);
		Vd_video.requestFocus();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏除app标题栏 需放setContentView之前
		setContentView(R.layout.activity_video); 
		
		Bn_Next = (Button)findViewById(R.id.Bn_Next);
		Bn_Per = (Button)findViewById(R.id.Bn_Pre);
		Ed_strUrl = (EditText)findViewById(R.id.Ed_Url);
		Vd_video = (VideoView)findViewById(R.id.Vd_video);
		Bn_Load = (Button)findViewById(R.id.Bn_load);
		Bn_start = (Button)findViewById(R.id.Bn_start);
		Bn_pause = (Button)findViewById(R.id.Bn_pause);
		Bn_stopPlayback = (Button)findViewById(R.id.Bn_stopPlayback);
		
		Bn_Next.setOnClickListener(l);
		Bn_Per.setOnClickListener(l);
		Bn_Load.setOnClickListener(l);
		Bn_start.setOnClickListener(l);
		Bn_pause.setOnClickListener(l);
		Bn_stopPlayback.setOnClickListener(l);
		
		Ed_strUrl.setHint(str_url);
		
	}
}
