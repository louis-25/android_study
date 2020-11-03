package com.example.example4_8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("연습문제 4-8");
        editText = findViewById(R.id.editText1);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { //입력하기 전에

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // 입력되는 텍스트에 변화가 있을 때
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            public void afterTextChanged(Editable s) { //입력이 끝났을 때

            }
        });




    }
}
