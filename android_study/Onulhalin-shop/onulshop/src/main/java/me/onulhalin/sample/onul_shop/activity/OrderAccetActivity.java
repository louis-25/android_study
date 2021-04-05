package me.onulhalin.sample.onul_shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.MainActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OrderAccetActivity extends Activity{
    Intent intent;
    int stock;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_dialog);

        Button cancle =(Button)findViewById(R.id.cancel_button);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button btn =(Button)findViewById(R.id.apply_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override protected void onDestroy() {
        super.onDestroy(); //앱의 전반적으로 돌아가던 스레드를 종료할 때 이 Debug.stopMethodTracing();

        }


}/* NumberPickerActivity */