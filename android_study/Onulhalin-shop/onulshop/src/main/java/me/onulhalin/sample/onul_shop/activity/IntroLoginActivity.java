package me.onulhalin.sample.onul_shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import me.onulhalin.sample.R;

public class IntroLoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrologin);
        initView();
    }

    public void initView(){
        email=(TextView)findViewById(R.id.email);
        email.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email:
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
