package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final TextView registerTextView = (TextView) findViewById(R.id.registerTextView);

        registerTextView.setOnClickListener(new View.OnClickListener() { //registerButton이 눌렸을때 실행하는 함수
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class); //LoginActivity에서 RegisterActivity로 넘어간다.
                LoginActivity.this.startActivity(registerIntent); //위의 인텐드 실행
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //서버 통신시 성공여부 파악
                            JSONObject jsonObject = new JSONObject(response); //JSONObject를 만들어 특정 Response를 실행했을 때 결과값을 저장할 수 있도록 함
                            boolean success = jsonObject.getBoolean("success"); //php의 success가 true값이면 로그인 성공!

                            if (success) { //로그인에 성공한 경우
                                String userID = jsonObject.getString("userID");
                                String userPassword = jsonObject.getString("userPassword");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class); //LoginActivity로 넘어가는 인텐트
                                intent.putExtra("userID",userID);
                                intent.putExtra("userPassword",userPassword);
                                Toast.makeText(getApplicationContext(),"로그인에 성공했습니다.",Toast.LENGTH_SHORT).show();//토스트 메세지
                                startActivity(intent);
                            }
                            else { //로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();//토스트 메세지
                                return;
                            }
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getApplicationContext(),"예외처리가 발생했습니다.",Toast.LENGTH_SHORT).show();//토스트 메세지
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });


    }


}
