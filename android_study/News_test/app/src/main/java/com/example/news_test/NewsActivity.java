package com.example.news_test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RequestQueue queue; //데이터를 받아줄 큐

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Log.w("test1","안뇽");
        // 요청데이터를 큐형식으로 받는다
        queue = Volley.newRequestQueue(this);
        getNews();
        Log.w("test2","안뇽");
        //1. 화면이 로딩 --> 뉴스 정보를 받아온다
        //2. 정보 --> 어댑터 넘겨준다
        //3. 어댑터 --> 셋팅

    }
    public void getNews() {
        //News API
        String url ="http://newsapi.org/v2/top-headlines?country=kr&apiKey=2be9726c91714c6d8de2746cbbdd36d2";
        Log.w("test3","안뇽");
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("NEWS",response); //로그찍어보기
                        Log.w("test4","안뇽");
                        //response -> NewsData Class분류
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Log.w("test","gkdsjgklsjfjwiojefiojwioejfoi");
                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");
                            //article이라는 이름을가진 JSONArray를 가져온다
                            jsonObj.getJSONArray("articles");
                            //response ->> NewsData Class 분류
                            List<NewsData> news = new ArrayList<>();

                            //JSONString 배열의 내용을 가져온다
                            for(int i=0, j = arrayArticles.length(); i<j; i++){
                                JSONObject obj = arrayArticles.getJSONObject(i);

                                Log.d("NEWS",obj.toString());

                                obj.getString("title");
                                obj.getString("urlToImage");
                                obj.getString("content");

                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setContent(obj.getString("content"));

                                news.add(newsData);
                            }

                            // 어댑터에 정보를 넘겨준다
                            //mAdapter = new MyAdapter(news, NewsActivity.this);
                            mAdapter = new MyAdapter(news, NewsActivity.this);
                            recyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    println("에러 =>" +error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
