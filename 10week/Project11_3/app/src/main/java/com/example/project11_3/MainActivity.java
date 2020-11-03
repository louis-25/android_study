package com.example.project11_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("스피너 테스트");

        final Integer[] movieID = {R.drawable.mov01, R.drawable.mov02, R.drawable.mov03,
                R.drawable.mov04, R.drawable.mov05, R.drawable.mov06,
                R.drawable.mov07, R.drawable.mov08, R.drawable.mov09,
                R.drawable.mov10};

        final String[] movie = {
                "프리즌 이스케이프", "마이스파이", "카페 벨에포크",
                "더 플랫폼", "패왕별희 디오리지널", "트롤:월드투어",
                "위대한 쇼맨", "레이니 데이 인 뉴욕", "톰보이",
                "저 산 너머"};

        Spinner spinner = findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,movie);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView ivMovie = findViewById(R.id.ivMovie);
                ivMovie.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivMovie.setImageResource(movieID[position]);
                //Log.i("position", pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
}
