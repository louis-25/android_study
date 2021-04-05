package com.example.example10_16;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메인 액티비티");

        Button btnNewActivity = findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtNum1 = findViewById(R.id.edtNum1);
                EditText edtNum2 = findViewById(R.id.edtNum2);
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                intent.putExtra("Num1",Integer.parseInt(edtNum1.getText().toString()));
                intent.putExtra("Num2",Integer.parseInt(edtNum2.getText().toString()));
                startActivityForResult(intent, 0); //서브액티비티에서 받을값이 있으면 두번째 인자에 0
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
