package com.example.project10_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton second, third;
    Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radio_group);
        second = findViewById(R.id.radio_second);
        third = findViewById(R.id.radio_third);
        change = findViewById(R.id.change_button);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                if(second.isChecked()){
                    intent = new Intent(getApplicationContext(),SecondActivity.class);
                    startActivity(intent);
                }
                else if(third.isChecked()){
                    intent = new Intent(getApplicationContext(),ThirdActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"메뉴를 선택해주세요",Toast.LENGTH_SHORT).show();
                }

            }
        });

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId){
//                    case R.id.radio_second:
//                        intent = new Intent(getApplicationContext(),SecondActivity.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.radio_third:
//                        intent = new Intent(getApplicationContext(),ThirdActivity.class);
//                        startActivity(intent);
//                        break;
//                }
//            }
//        });



    }
}
