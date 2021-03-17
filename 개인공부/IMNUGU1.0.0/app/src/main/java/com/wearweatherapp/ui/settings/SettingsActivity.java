package com.wearweatherapp.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.wearweatherapp.ui.main.MainActivity;
import com.wearweatherapp.util.AddressParsingUtil;
import com.wearweatherapp.util.GpsTracker;
import com.wearweatherapp.util.PreferenceManager;
import com.wearweatherapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();
    private FrameLayout address_find;
    private FrameLayout redirect;
    private FrameLayout favorites;
    private String location_name;
    private ImageView backBtn;
    private FrameLayout device_opt;


    private FrameLayout opensource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        final String loginuid = intent.getStringExtra("_id");
        final String deviceId = intent.getStringExtra("deviceId");
        final String deviceName = intent.getStringExtra("deviceName");
        final String loginActive = intent.getStringExtra("IsActive");
        Log.d(TAG, "_id "+loginuid);
        Log.d(TAG, "deviceId "+deviceId);
        Log.d(TAG, "deviceName "+deviceName);
        Log.d(TAG, "IsActive "+loginActive);


        address_find= findViewById(R.id.search_layout);
        address_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        redirect= findViewById(R.id.redirect_layout);
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GpsTracker gpsTracker = new GpsTracker(SettingsActivity.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                PreferenceManager.setFloat(SettingsActivity.this,"LATITUDE",(float)latitude);
                PreferenceManager.setFloat(SettingsActivity.this,"LONGITUDE",(float)longitude);

                String address = getCurrentAddress(latitude, longitude);
                address = AddressParsingUtil.getSigunguFromFullAddress(address);

                Toast.makeText(SettingsActivity.this, "주소가 "+address+"로 설정되었습니다",Toast.LENGTH_SHORT).show();

                PreferenceManager.setBoolean(SettingsActivity.this,"IS_ADDRESS_CHANGED",true);
                PreferenceManager.setString(getApplicationContext(),"CITY",address);
            }
        });

        device_opt= findViewById(R.id.device_layout);
        device_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeviceOption.class);
                intent.putExtra("_id", loginuid);
                intent.putExtra("deviceId", deviceId);
                intent.putExtra("deviceName", deviceName);
                intent.putExtra("isActive", loginActive);

                Log.d(TAG, "_id "+loginuid);
                Log.d(TAG, "deviceId "+deviceId);
                Log.d(TAG, "deviceName "+deviceName);
                Log.d(TAG, "isActive "+loginActive);
                startActivity(intent);
            }
        });

        backBtn = (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }

    public String getCurrentAddress( double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude,longitude,7);
        } catch (IOException ioException) {
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

}