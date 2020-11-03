package com.example.project4_2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CheckBox chkAgree;
    RadioGroup radio_group;
    RadioButton radio_cat;
    RadioButton radio_dog;
    RadioButton radio_rabbit;
    Button btnOK;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chkAgree = findViewById(R.id.chkAgree);
        radio_group = findViewById(R.id.radio_group);
        radio_cat = findViewById(R.id.cat);
        radio_dog = findViewById(R.id.dog);
        radio_rabbit = findViewById(R.id.rabbit);
        btnOK = findViewById(R.id.button);
        image = findViewById(R.id.Image);

        chkAgree.setOnClickListener(new CheckBox.OnClickListener() { //체크박스가 클릭되었을때
            @Override
            public void onClick(View v) {
                if(chkAgree.isChecked() == true) {
                    btnOK.setVisibility(View.VISIBLE);
                    radio_group.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                } else {
                    btnOK.setVisibility(View.INVISIBLE);
                    radio_group.setVisibility(View.INVISIBLE);
                    image.setVisibility(View.INVISIBLE);
                }
            }

        });

        btnOK.setOnClickListener(new Button.OnClickListener() { //버튼이 클릭되었을때
            @Override
            public void onClick(View v) {
                switch(radio_group.getCheckedRadioButtonId()){
                    case R.id.dog:
                        image.setImageResource(R.drawable.dog);
                        break;
                    case R.id.cat:
                        image.setImageResource(R.drawable.cat);
                        break;
                    case R.id.rabbit:
                        image.setImageResource(R.drawable.rabbit);
                        break;
                }
            }
        });



    }
}
