package me.onulhalin.sample.onul_shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.onulhalin.sample.onul_shop.activity.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences setting;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setting = getSharedPreferences("setting", 0);
       if (!setting.getString("ID", "").equals("") ){ //&& !setting.getString("addr", "").equals("")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
