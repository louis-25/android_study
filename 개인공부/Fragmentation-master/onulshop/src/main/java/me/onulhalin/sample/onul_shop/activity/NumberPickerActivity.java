package me.onulhalin.sample.onul_shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import me.onulhalin.sample.R;

public class NumberPickerActivity extends Activity{
    Intent intent;
    int stock;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_day_dialog);
        Button btn =(Button)findViewById(R.id.apply_button);


        NumberPicker np = (NumberPicker)findViewById(R.id.number_picker);

         intent = getIntent();
        intent.getStringExtra("position");
      //  Toast.makeText(this, ""+intent.getStringExtra("position"), Toast.LENGTH_SHORT).show();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",intent.getStringExtra("position"));
                resultIntent.putExtra("stock",String.valueOf(stock));
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
        np.setMinValue(0);// restricted number to minimum value i.e 1
        np.setMaxValue(100);// restricked number to maximum value i.e. 31
//        np.setValue(Integer.parseInt(intent.getStringExtra("stock")));
        np.setWrapSelectorWheel(false);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {

                // TODO Auto-generated method stub


                String Old = "Old Value : ";

                String New = "New Value : ";
                stock = newVal;

            }
        });

        Log.d("NumberPicker", "NumberPicker");

    }
    @Override protected void onDestroy() {
        super.onDestroy(); //앱의 전반적으로 돌아가던 스레드를 종료할 때 이 Debug.stopMethodTracing();

        }


}/* NumberPickerActivity */