package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText et_number;
    Button add,sub,mul,div,del,result;

    String history ="";
    String number1 ="";
    String number2 ="";

    int type;

    int ADD = 0;
    int SUB = 1;
    int MUL = 2;
    int DIV = 3;
    double d1;
    double d2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_number = findViewById(R.id.number);
        et_number.setText("");
        add = findViewById(R.id.plus);          // +
        sub = findViewById(R.id.minus);        // -
        mul = findViewById(R.id.mul);         // *
        div = findViewById(R.id.divide);     // /
        del = findViewById(R.id.backspace); // <-
        result = findViewById(R.id.equal); // =


       Button.OnClickListener mListner = new Button.OnClickListener() {
           public void onClick(View v) {
               switch (v.getId()) {
                   case R.id.plus:
                       type = ADD;
                       et_number.setText(et_number.getText().toString()+ " + ");
                       break;
                   case R.id.minus:
                       type = SUB;
                       et_number.setText(et_number.getText().toString()+ " - ");
                       break;
                   case R.id.mul:
                       type = MUL;
                       et_number.setText(et_number.getText().toString()+ " * ");
                       break;
                   case R.id.divide:
                       type = DIV;
                       et_number.setText(et_number.getText().toString()+ " / ");
                       break;
               }

           }

       };


    }


    public void onClick(View v) {
             switch(v.getId()){
                case R.id.n0 : et_number.setText(et_number.getText().toString()+0); break;
                case R.id.n1 : et_number.setText(et_number.getText().toString()+1); break;
                case R.id.n2 : et_number.setText(et_number.getText().toString()+2); break;
                case R.id.n3 : et_number.setText(et_number.getText().toString()+3); break;
                case R.id.n4 : et_number.setText(et_number.getText().toString()+4); break;
                case R.id.n5 : et_number.setText(et_number.getText().toString()+5); break;
                case R.id.n6 : et_number.setText(et_number.getText().toString()+6); break;
                case R.id.n7 : et_number.setText(et_number.getText().toString()+7); break;
                case R.id.n8 : et_number.setText(et_number.getText().toString()+8); break;
                case R.id.n9 : et_number.setText(et_number.getText().toString()+9); break;
            }

    }
}
