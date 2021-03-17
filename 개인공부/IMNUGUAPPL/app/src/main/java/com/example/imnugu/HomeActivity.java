package com.example.imnugu;

//import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

public class HomeActivity extends Activity {

    private TextView tv_result; //닉네임 text
    private ImageView iv_profile; //이미지 뷰
    private TextView tv_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String getID = intent.getStringExtra("getID"); //MainActivity로 부터 닉네임 전달받음
        String photoUrl = intent.getStringExtra("photoUrl"); //MainActivity로 부터 포토URL 전달받음
        String email = intent.getStringExtra("email");

        tv_result = findViewById(R.id.tv_result);
        tv_result.setText(getID);       //닉네임 text를 텍스트 뷰에 세팅

        tv_email = findViewById(R.id.tv_email);
        tv_email.setText(email);

        iv_profile = findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile); // 프로필url을 이미지뷰에 세팅

    }
}