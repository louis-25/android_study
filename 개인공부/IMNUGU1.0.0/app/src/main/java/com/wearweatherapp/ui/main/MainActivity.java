package com.wearweatherapp.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.wearweatherapp.statusResult;
import com.wearweatherapp.ui.main.adapter.MainTabPagerAdapter;
import com.wearweatherapp.ui.settings.DeviceOption;
import com.wearweatherapp.util.GpsTracker;
import com.wearweatherapp.util.PreferenceManager;
import com.wearweatherapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TabLayout tabLayout;
    private MainTabPagerAdapter pagerAdpater;
    private ViewPager viewPager;

    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        final Intent intent = getIntent();
        String getID = intent.getStringExtra("getID"); //MainActivity로 부터 닉네임 전달받음
        final String email = intent.getStringExtra("email");

        //if(PreferenceManager.getBoolean(this, "MAIN_NOTICE_DIALOG")!=true){
        //    showMainNoticeDialog();
        //}
        Log.d(TAG, "Login Success After Action Check");

        JsonObject json_dev = new JsonObject();
        json_dev.addProperty("userId", email);

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
            System.out.println("IsActive = "+IsActive);
            System.out.println("_id = "+_id);
            PreferenceManager.setString(getApplicationContext(),"_id",_id);
            PreferenceManager.setString(getApplicationContext(),"IsActive",IsActive);
            PreferenceManager.setString(getApplicationContext(),"deviceId",deviceId);
            PreferenceManager.setString(getApplicationContext(),"deviceName",deviceName);

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

        //여기서 문제임
        /*
        Intent intent_dev = new Intent(MainActivity.this, DeviceOption.class);
        intent_dev.putExtra("_id", final_id);
        intent_dev.putExtra("isActive", finalIsActive);
        intent_dev.putExtra("deviceName", final_deviceName);
        intent_dev.putExtra("deviceId", final_deviceId);


         */
    }

    private void initSharedPreference(){
        Log.e("SEULGI SP", ""+PreferenceManager.getFloat(this, "LATITUDE"));
        Log.e("SEULGI SP", ""+PreferenceManager.getFloat(this, "LONGITUDE"));
        if(PreferenceManager.getFloat(this, "LATITUDE")==-1F){
            if(latitude!=0.0 && longitude!=0.0){
                PreferenceManager.setFloat(this,"LATITUDE",(float)latitude);
            }
            else {
                PreferenceManager.setFloat(this,"LATITUDE",37.5172f);
            }
        }
        if(PreferenceManager.getFloat(this, "LONGITUDE")==-1F){
            if(latitude!=0.0 && longitude!=0.0){
                PreferenceManager.setFloat(this,"LONGITUDE",(float)longitude);
            }
            else {
                PreferenceManager.setFloat(this,"LONGITUDE",127.0473f);
            }
        }
        if(PreferenceManager.getString(this, "CITY").equals("")){
            PreferenceManager.setString(getApplicationContext(),"CITY","서울특별시 강남구");
        }

        PreferenceManager.setInt(this, "REGION_NUMBER",1);
    }

    private void initView() {
        /* tab layout */
        tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        pagerAdpater = new MainTabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

        int region_number=PreferenceManager.getInt(this,"REGION_NUMBER");

        pagerAdpater.initFragment(region_number);
        for(int i=0;i<region_number;i++){
            tabLayout.addTab(tabLayout.newTab());
        }

        viewPager = (ViewPager) findViewById(R.id.main_tab_viewpager);
        viewPager.setAdapter(pagerAdpater);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerAdpater.notifyDataSetChanged();
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdpater.getWeatherFragment(tab.getPosition()).displayWeather(getApplicationContext());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void showMainNoticeDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("앱 처음 실행 시 공지")
                .setMessage("설정창에 들어가서 날씨 정보를 받을 위치를 설정하세요!")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("다시 보지 않기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PreferenceManager.setBoolean(getApplicationContext(), "MAIN_NOTICE_DIALOG",true);
                    }
                })
                .show();
    }


}