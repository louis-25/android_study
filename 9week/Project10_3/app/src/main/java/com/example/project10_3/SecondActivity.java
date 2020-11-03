package com.example.project10_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class SecondActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        setTitle("Second 액티비티");

        Button btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            int Value = 0;
            Intent inIntent = getIntent();
            final String sign = inIntent.getStringExtra("sign");
            @Override
            public void onClick(View v) {
                switch(sign){
                    case "add":
                        Value = inIntent.getIntExtra("Num1",0) + inIntent.getIntExtra("Num2",0);
                        break;
                    case "min":
                        Value = inIntent.getIntExtra("Num1",0) - inIntent.getIntExtra("Num2",0);
                        break;
                    case "mul":
                        Value = inIntent.getIntExtra("Num1",0) * inIntent.getIntExtra("Num2",0);
                        break;
                    case "div":
                        Value = inIntent.getIntExtra("Num1",0) / inIntent.getIntExtra("Num2",0);
                        break;
                }
                Intent outIntent = new Intent(getApplicationContext(),MainActivity.class);
                outIntent.putExtra("Hap", Value);
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

    }
}
