package com.example.project10_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class ResultActivity extends AppCompatActivity {

    ImageView ivTop;
    TextView tvTop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle("투표 결과");


        Intent intent = getIntent();
        int[] voteResult = intent.getIntArrayExtra("VoteCount");//VoteCount라는 태그를 가진 Int형 배열을 가져온다
        String[] imageName = intent.getStringArrayExtra("ImageName");

        TextView[] tv = new TextView[imageName.length];
        RatingBar[] rbar = new RatingBar[imageName.length];


        Integer[] tvID = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5,
                R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9};
        Integer[] rbarID = {R.id.rbar1, R.id.rbar2, R.id.rbar3, R.id.rbar4, R.id.rbar5,
                R.id.rbar6, R.id.rbar7, R.id.rbar8, R.id.rbar9};
        Integer[] imageFileId = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
                R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
                R.drawable.pic1, R.drawable.pic2, R.drawable.pic3};

        //Top
        ivTop = findViewById(R.id.ivTop);
        tvTop = findViewById(R.id.tvTop);
        int max = 0;
        for(int i = 0; i<voteResult.length; i++) { // 1등 그림 이름과 그림 파일을 보여준다
            if (voteResult[max] < voteResult[i])
                max = i;
        }
        ivTop.setImageResource(imageFileId[max]);
        tvTop.setText(imageName[max]);

        for(int i = 0; i<voteResult.length; i++) { // TextView, RatingBar 개체 찾기
            tv[i] = (TextView) findViewById(tvID[i]);
            rbar[i] = (RatingBar) findViewById(rbarID[i]);
        }

        for(int i = 0; i<voteResult.length; i++) { //각 TextView 및 RatingBar에 넘겨 받은 값을 반영
            tv[i].setText(imageName[i]);
            rbar[i].setRating((float) voteResult[i]);
        }

        Button btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // main 액티비티로 돌아감
            }
        });
    }
}
