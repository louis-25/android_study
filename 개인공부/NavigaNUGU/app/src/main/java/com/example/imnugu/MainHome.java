package com.example.imnugu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainHome extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private View drawerView;

    private TextView NUGU_User; //닉네임 text
    private TextView NUGU_email; //닉네임 text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        final Intent intent = getIntent();
        String getID = intent.getStringExtra("getID"); //MainActivity로 부터 닉네임 전달받음
        final String email = intent.getStringExtra("email");

        NUGU_User = findViewById(R.id.NUGU_User);
        NUGU_User.setText(getID);

        NUGU_email = findViewById(R.id.NUGU_email);
        NUGU_email.setText(email);

        //여기서 다시시작

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        Button btn_open = (Button)findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }
        });


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
        String deviceId ="";
        String deviceName ="";
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
                deviceId += order.getString("deviceId");
                deviceName += order.getString("deviceName");
            }
            System.out.println("deviceId = "+deviceId);
            System.out.println("deviceName = "+deviceName);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final String final_deviceName = deviceName;
        final String final_deviceId = deviceId;
        final String final_id = _id;
        final String finalIsActive = IsActive;
        Button btn_opt = (Button)findViewById(R.id.btn_opt);
        btn_opt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(drawerView);
                //여기에 이동 추가

                Intent intent_dev = new Intent(MainHome.this, DeviceOptionActivuty.class);
                intent_dev.putExtra("_id", final_id);
                intent_dev.putExtra("isActive", finalIsActive);
                intent_dev.putExtra("deviceName", final_deviceName);
                intent_dev.putExtra("deviceId", final_deviceId);

                startActivity(intent_dev);
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

}