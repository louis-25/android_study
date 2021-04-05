package com.example.imnugu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class DeviceOptionActivuty extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView device_id;
    private TextView device_name;
    private TextView ActiveStatus;
    private Button StatusChange;
    private Button saveDevOpt;
    String status = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_option_activuty);

        Intent intent = getIntent();
        String loginuid = intent.getStringExtra("_id");
        final String deviceId = intent.getStringExtra("deviceId");
        final String deviceName = intent.getStringExtra("deviceName");
        final String loginActive = intent.getStringExtra("isActive");
        status = loginActive;


        device_id = findViewById(R.id.device_id);
        device_id.setText(deviceId);
        device_name = findViewById(R.id.device_name);
        device_name.setText(deviceName);
        ActiveStatus = findViewById(R.id.ActiveStatus);
        //ActiveStatus.setText(loginActive);

        String url_off = new String();
        url_off += "http://35.232.181.79:3000/iot/off/" + loginuid;
        System.out.println("urlid : " +url_off);
        final OkHttpClient client_off = new OkHttpClient();


        String url_on = new String();
        url_on += "http://35.232.181.79:3000/iot/on/" + loginuid;
        System.out.println("urlid : " +url_on);
        final OkHttpClient client_on = new OkHttpClient();


        final String finalUrl_off = url_off;
        final String finalUrl_on = url_on;

        final AsyncTask<Void, Void, String> asyncTask_on = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Request request_on = new Request.Builder()
                            .url(finalUrl_on)
                            .build();
                    Response response = client_on.newCall(request_on).execute();
                    Log.d(TAG, "data = " + response);
                    if (!response.isSuccessful()) {
                        return null;
                    }
                    Log.d(TAG, "data2 = " + response);
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
                    System.out.println("asdfqwer : " + s);
                }
            }
        };

        final AsyncTask<Void, Void, String> asyncTask_off = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Request request_off = new Request.Builder()
                            .url(finalUrl_off)
                            .build();
                    Response response = client_off.newCall(request_off).execute();
                    Log.d(TAG, "data = " + response);
                    if (!response.isSuccessful()) {
                        return null;
                    }
                    Log.d(TAG, "data2 = " + response);
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
                    System.out.println("asdfqwer : " + s);
                }
            }
        };
/*
        StatusChange = findViewById(R.id.StatusChange);
        StatusChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("on")){
                    System.out.println("status : off");
                    ActiveStatus.setText("off");
                    status = "off";

                }
                else {
                    System.out.println("status : on");
                    ActiveStatus.setText("on");
                    status = "on";
                }

            }
        });

 */

        saveDevOpt = findViewById(R.id.saveDevOpt);
        saveDevOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("on")){
                    asyncTask_on.execute();
                }
                else {
                    asyncTask_off.execute();
                }
                Toast.makeText(DeviceOptionActivuty.this, "설정 변경 완료", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //final TextView optionState = (TextView)findViewById(R.id.textView);


        // 스위치 버튼입니다.
        SwitchButton switchButton = (SwitchButton) findViewById(R.id.sb_use_listener);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                // 스위치 버튼이 체크되었는지 검사하여 텍스트뷰에 각 경우에 맞게 출력합니다.
                if (isChecked){

                    ActiveStatus.setText("장치 활성화 하기");
                    status = "on";

                }else{

                    ActiveStatus.setText("장치 비활성화 하기");
                    status = "off";
                }
            }
        });

    }
}