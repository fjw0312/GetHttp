# GetHttp
请求获取http 个人简单测试工具


所用知识：http网络请求（url urlConnection HttpURLConnection HttpClient）、 volley、 MediaPlay、 video、webView、Json解析 等

功能：个人 http 简单的网路请求测试工具。  

测试 url 网络资源请求获取  url.openStream()    eg:网络图片

测试 urlConnetction 网络资源\数据 获取    urlConnection = url.openConnection();

测试 HttpURLConnetction 网络资源\数据 获取    HttpURLConnection = url.openConnection();

测试 HttpClient 网络资源\数据 获取
  HttpClient httpClient = new DefaultHttpClient();  
  HttpGet httpGet = new HttpGet(strUrl); 
  HttpResponse httpResponse = httpClient.execute(httpGet);
  
测试网络开源框架Volley的网络数据请求测试
  RequestQueue mQueue = Volley.newRequestQueue(context);
  StringRequest stringRequest = new StringRequest(url,Listener,ErrorListener);
  mQueue.add(stringRequest);
  
测试WebView 的使用

测试 网络视频播放。
