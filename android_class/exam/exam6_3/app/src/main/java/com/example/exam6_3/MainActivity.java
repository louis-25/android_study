package com.example.exam6_3;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;

import java.util.BitSet;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    ImageView dog, cat, rabbit, horse; //이미지뷰
    Button rotate; //회전버튼
    EditText editText; //회전값 입력


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("연습문제6_3");

        dog = findViewById(R.id.dog);
        cat = findViewById(R.id.cat);
        rabbit = findViewById(R.id.rabbit);
        horse = findViewById(R.id.horse);

        rotate = findViewById(R.id.btn1);
        editText = findViewById(R.id.editText1);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tab_Dog = tabHost.newTabSpec("Animal").setIndicator("강아지");
        tab_Dog.setContent(R.id.dog);
        tabHost.addTab(tab_Dog);

        TabHost.TabSpec tab_Cat = tabHost.newTabSpec("Animal").setIndicator("고양이");
        tab_Cat.setContent(R.id.cat);
        tabHost.addTab(tab_Cat);

        TabHost.TabSpec tab_Rabbit = tabHost.newTabSpec("Animal").setIndicator("토끼");
        tab_Rabbit.setContent(R.id.rabbit);
        tabHost.addTab(tab_Rabbit);

        TabHost.TabSpec tab_Horse = tabHost.newTabSpec("Animal").setIndicator("말");
        tab_Horse.setContent(R.id.horse);
        tabHost.addTab(tab_Horse);

        tabHost.setCurrentTab(0);

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y = Integer.parseInt(editText.getText().toString()); //회전값 추출
                //회전값 세팅
                dog.setRotationY(y);
                cat.setRotationY(y);
                rabbit.setRotationY(y);
                horse.setRotationY(y);
            }
        });

    }


}