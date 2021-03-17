package com.example.imnugu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView tv_result; //닉네임 text
    private ImageView iv_profile; //이미지 뷰
    private TextView tv_email;
    private Button btn_device;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Intent intent = getIntent();
        String getID = intent.getStringExtra("getID"); //MainActivity로 부터 닉네임 전달받음
        String photoUrl = intent.getStringExtra("photoUrl"); //MainActivity로 부터 포토URL 전달받음
        final String email = intent.getStringExtra("email");

        tv_result = findViewById(R.id.tv_result);
        tv_result.setText(getID);       //닉네임 text를 텍스트 뷰에 세팅

        tv_email = findViewById(R.id.tv_email);
        tv_email.setText(email);

        iv_profile = findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile); // 프로필url을 이미지뷰에 세팅

        ///////////

        JsonObject json_dev = new JsonObject();
        json_dev.addProperty("userId", "luninechoi@gmail.com");

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://35.232.181.79:3000/iot/device/list")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        json_dev.toString()))
                .build();


        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                List<statusResult> tkList = new ArrayList<>();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d(TAG, "data = "+response);
                    System.out.println(response);



                    if (!response.isSuccessful()) {
                        return null;
                    }
                    Log.d(TAG, "data2 = "+response);
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }

            @Override
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    System.out.println(s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String Status = jsonObject.getString("status");
                        String result = jsonObject.getString("result");
                        String list = jsonObject.getString("list");
                        System.out.println(list);


                        if(!list.equals("[]")){
                            //list안에 값이 있음. 즉, 디바이스 정보가 있다
                        }
                        else{
                            //list안에 값이 없음, 즉, 디바이스 정보가 없다
                            //여기에 디바이스 설정 activity로 이동하는 클래스로 이동하는거 넣어야함
                        }

                        /*
                        AsyncBufferStatus[0] = Status;
                        System.out.println(AsyncBufferStatus[0]);
                        AsyncBufferResult[0] = result;
                        System.out.println(AsyncBufferResult[0]);

                         */
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }


        };
        asyncTask.execute();

        String _id ="";
        String IsActive = "";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(asyncTask.get());
            String list = jsonObject.getString("list");
            System.out.println("asd : "+list);

            JSONArray ja = new JSONArray(list);

            for(int i = 0 ; i < ja.length() ; i++){
                JSONObject order = ja.getJSONObject(i);
                _id += order.getString("_id");
                IsActive += order.getString("isActive");
            }
            System.out.println("_id = "+_id);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btn_device = findViewById(R.id.btn_device);
        final String final_id = _id;
        final String finalIsActive = IsActive;
        btn_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_dev = new Intent(HomeActivity.this, DeviceOptionActivuty.class);
                intent_dev.putExtra("email", email);
                intent_dev.putExtra("_id", final_id);
                intent_dev.putExtra("isActive", finalIsActive);
                startActivity(intent_dev);
            }
        });

    }
}