package me.onulhalin.sample.onul_shop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.MainActivity;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackDevice;
import me.onulhalin.sample.onul_shop.model.login;
import me.onulhalin.sample.onul_shop.model.pushKey;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.onulhalin.sample.onul_shop.data.CoreData.sToken;

public class LoginActivity extends OHActivitiy implements View.OnClickListener {

    EditText adminid,adminpw;
    Button btn,join_btn;
    SharedPreferences setting;

    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();

    }



    private void initView(){
        setTitle("이메일로 로그인");
        adminid=(EditText)findViewById(R.id.adminid);
        adminid.setHint("example@gamil.com");
        adminpw=(EditText)findViewById(R.id.adminpw);
        adminpw.setHint("비밀번호 입력(6~12자)");
        btn = (Button)findViewById(R.id.btn);
        join_btn= (Button)findViewById(R.id.join_btn);
    }

    private void initData(){
        btn.setOnClickListener(this);
        join_btn.setOnClickListener(this);
    }

    private void submitLoginData() {

        login user = new login();
        user.username = adminid.getText().toString();
        user.password = adminpw.getText().toString();



        API api = RestAdapter.createAPI();
        Call<CallbackDevice> callbackCall = api.registerDevice(user);

        callbackCall.enqueue(new Callback<CallbackDevice>() {
            @Override
            public void onResponse(Call<CallbackDevice> call, Response<CallbackDevice> response) {
                CallbackDevice resp = response.body();
                if (resp != null && resp.status.equals("success")) {
                //    Log.d("kimtest","@@" +resp.user.name);
                    //  sharedPref.setOpenAppCounter(0);
                    if (!sToken.equals(resp.shop_user.pushkey)&&!sToken.equals("")) {
                        submitpushKeyData(resp.shop_user.id, resp.shop_user.pushkey);
                    }
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();


                    setting = getSharedPreferences("setting", 0);

                    editor= setting.edit();


                    editor.putString("ID", adminid.getText().toString());

                    editor.putString("PW", adminpw.getText().toString());

                   // editor.putBoolean("Auto_Login_enabled", true);

                    editor.commit();

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (resp.status.equals("failed")){
                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CallbackDevice> call, Throwable t) {
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });
    }


    private void submitpushKeyData(String id,String pushkey) {

        pushKey user = new pushKey();
        user.id =id;
        if (sToken !=null||sToken.equals("null") ||sToken.equals(pushkey)) {
            user.pushkey = sToken;
        }


        API api = RestAdapter.createAPI();
        Call<CallbackDevice> callbackCall = api.pushkey(user);

        callbackCall.enqueue(new Callback<CallbackDevice>() {
            @Override
            public void onResponse(Call<CallbackDevice> call, Response<CallbackDevice> response) {
                CallbackDevice resp = response.body();

            }

            @Override
            public void onFailure(Call<CallbackDevice> call, Throwable t) {
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                if (adminid.getText().toString().equals("")){
                    Toast.makeText(this, "ID 를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(adminpw.getText().toString().equals("")){
                    Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    submitLoginData();
                }
                break;
            case R.id.join_btn:
                Intent i = new Intent(this, DebugActivity.class);
                startActivity(i);
                break;
        }
    }
}
