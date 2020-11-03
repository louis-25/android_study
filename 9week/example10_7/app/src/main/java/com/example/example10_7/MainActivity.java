package com.example.example10_7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RatingBar rating1, rating2, rating3;
        Button btnInc, btnDec;

        rating1 = findViewById(R.id.ratingBar1);
        rating2 = findViewById(R.id.ratingBar2);
        rating3 = findViewById(R.id.ratingBar3);
        btnInc =findViewById(R.id.btnInc);
        btnDec =findViewById(R.id.btnDec);

        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating1.setRating(rating1.getRating() + rating1.getStepSize()); //현재의 별의개수 + 스텝사이즈
                rating2.setRating(rating2.getRating() + rating2.getStepSize());
                rating3.setRating(rating3.getRating() + rating3.getStepSize());
            }
        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating1.setRating(rating1.getRating() - rating1.getStepSize()); //현재의 별의개수 - 스텝사이즈
                rating2.setRating(rating2.getRating() - rating2.getStepSize());
                rating3.setRating(rating3.getRating() - rating3.getStepSize());
            }
        });
    }
}
