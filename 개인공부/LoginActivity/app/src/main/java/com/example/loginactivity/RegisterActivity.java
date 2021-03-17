package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) { //액티비티 시작시 처음으로 실행되는 것!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toast.makeText(getApplicationContext(),"회원정보를 입력하세요.",Toast.LENGTH_SHORT).show();//토스트 메세지

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText ageText = (EditText) findViewById(R.id.ageText);

        //회원가입 버튼 클릭 시 수행
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //registerButton이 눌렸을때
                // 회원가입 입력값을 변수에 저장
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                int userAge = Integer.parseInt(ageText.getText().toString());
                //Toast.makeText(getApplicationContext(),"여기는 onClick 입니다.",Toast.LENGTH_SHORT).show();//토스트 메세지

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                                //서버 통신시 성공여부 파악
                                JSONObject jsonObject = new JSONObject(response); //JSONObject를 만들어 특정 Response를 실행했을 때 결과값을 저장할 수 있도록 함
                                boolean success = jsonObject.getBoolean("success");
                                Toast.makeText(getApplicationContext(),"여기는 try문 입니다.",Toast.LENGTH_SHORT).show();//토스트 메세지
                                if (success) { //회원등록에 성공한 경우
                                    Toast.makeText(getApplicationContext(),"회원 등록에 성공했습니다.",Toast.LENGTH_SHORT).show();//토스트 메세지
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class); //LoginActivity로 넘어가는 인텐트
                                    startActivity(intent);
                                }
                                else { //회원등록에 실패한 경우
                                    Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();//토스트 메세지
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
                // 서버로 Volley를 이용해서 요청을 함
                RegisterRequest registerRequest = new RegisterRequest(userID,userPassword,userName,userAge,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }

         });
    }



}
/*AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("회원 등록에 성공했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
*/
