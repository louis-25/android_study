package com.example.project10_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioGroup rdo_group;
//    RadioButton rdo_add, rdo_min, rdo_mul, rdo_div;
    public static boolean flag = true;
    public static String sign = "none";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메인 액티비티");

        rdo_group = findViewById(R.id.rdo_group);
//        rdo_add = findViewById(R.id.rdo_add);
//        rdo_min = findViewById(R.id.rdo_min);
//        rdo_mul = findViewById(R.id.rdo_mul);
//        rdo_div = findViewById(R.id.rdo_div);


        rdo_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rdo_add:
                        sign = "add";
                        Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rdo_sub:
                        sign = "sub";
                        Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rdo_mul:
                        sign = "mul";
                        Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rdo_div:
                        sign = "div";
                        Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

        Button btnNewActivity = findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sign != "none") { //부호가 선택되었을때
                    EditText edtNum1 = findViewById(R.id.edtNum1);
                    EditText edtNum2 = findViewById(R.id.edtNum2);
                    if(edtNum1.getText().toString().equals("") || edtNum2.getText().toString().equals("")) {//빈값이 넘어올때
                        Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                        intent.putExtra("Num1", Integer.parseInt(edtNum1.getText().toString()));
                        intent.putExtra("Num2", Integer.parseInt(edtNum2.getText().toString()));
                        intent.putExtra("sign", sign);
                        startActivityForResult(intent, 0); //서브액티비티에서 받을값이 있으면 두번째 인자에 0
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){ //사용자가 정상적으로 작업을 진행할때 (중간에 취소 :RESULT_CANCLED)
            int hap = data.getIntExtra("Hap",0);
            //창이 넘어갈때 메세지를 띄워줌
            Toast.makeText(getApplicationContext(),"합계"+hap,Toast.LENGTH_SHORT).show();
        }
    }
}
