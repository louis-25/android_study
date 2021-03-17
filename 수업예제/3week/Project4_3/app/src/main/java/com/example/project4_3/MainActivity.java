package com.example.project4_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edit1, edit2;
    Button btnAdd, btnSub, btnMul, btnDiv, btnRest;
    TextView textView, textResult;
    String num1, num2;
    Float result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("초간단 계산기");

        edit1 = (EditText) findViewById(R.id.Edit1);
        edit2 = (EditText) findViewById(R.id.Edit2);
        btnAdd = (Button) findViewById(R.id.BtnAdd);
        btnSub = (Button) findViewById(R.id.BtnSub);
        btnMul = (Button) findViewById(R.id.BtnMul);
        btnDiv = (Button) findViewById(R.id.BtnDiv);
        btnRest = (Button) findViewById(R.id.Div_rest);
        textResult = (TextView) findViewById(R.id.TextResult);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit1.getText().toString().equals("") || edit2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"숫자값을 입력해주세요",Toast.LENGTH_LONG).show();
                    return;
                }
                num1 = edit1.getText().toString();
                num2 = edit2.getText().toString();
                result = Float.parseFloat(num1) + Float.parseFloat(num2);
                textResult.setText("계산 결과 : " + result.toString());

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit1.getText().toString().equals("") || edit2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"숫자값을 입력해주세요",Toast.LENGTH_LONG).show();
                    return;
                }
                num1 = edit1.getText().toString();
                num2 = edit2.getText().toString();
                result = Float.parseFloat(num1) - Float.parseFloat(num2);
                textResult.setText("계산 결과 : "+ result.toString());

            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit1.getText().toString().equals("") || edit2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"숫자값을 입력해주세요",Toast.LENGTH_LONG).show();
                    return;
                }
                num1 = edit1.getText().toString();
                num2 = edit2.getText().toString();
                result = Float.parseFloat(num1) * Float.parseFloat(num2);
                textResult.setText("계산 결과 : "+ result.toString());

            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit1.getText().toString().equals("") || edit2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"숫자값을 입력해주세요",Toast.LENGTH_LONG).show();
                    return;
                }

                num1 = edit1.getText().toString();
                num2 = edit2.getText().toString();
                if(Float.parseFloat(num1)==0 || Float.parseFloat(num2)==0) {
                    Toast.makeText(getApplicationContext(),"0으로 나눌 수 없습니다.",Toast.LENGTH_LONG).show();
                    return;
                }
                result = Float.parseFloat(num1) / Float.parseFloat(num2);
                textResult.setText("계산 결과 : "+ result.toString());

            }
        });

        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit1.getText().toString().equals("") || edit2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"숫자값을 입력해주세요",Toast.LENGTH_LONG).show();
                    return;
                }

                num1 = edit1.getText().toString();
                num2 = edit2.getText().toString();
                if(Float.parseFloat(num1)==0 || Float.parseFloat(num2)==0) {
                    Toast.makeText(getApplicationContext(),"0으로 나눌 수 없습니다.",Toast.LENGTH_LONG).show();
                    return;
                }
                result = Float.parseFloat(num1) % Float.parseFloat(num2);
                textResult.setText("계산 결과 : "+ result.toString());
            }
        });


    }
}
