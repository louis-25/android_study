package me.onulhalin.sample.onul_shop.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackJoin;
import me.onulhalin.sample.onul_shop.model.login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinShopInfoActivity extends OHActivitiy implements View.OnClickListener {


    EditText member_addr,member_introduce;
    Button cerificaton_check, send;
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joininfo);
        setting = getSharedPreferences("setting", 0);
        initview();
        initData();
    }


    private void initview(){
        member_addr = (EditText)findViewById(R.id.member_addr);
        member_introduce =(EditText)findViewById(R.id.member_introduce);

        cerificaton_check = (Button)findViewById(R.id.cerificaton_check);
        cerificaton_check.setOnClickListener(this);

        setTitle("상점 회원가입");
    }

    private void initData(){

    }
    private void initCheck(){

    }


    private void submitOrderData() {

        login user = new login();
        user.addr = member_addr.getText().toString();
        user.introduce = member_introduce.getText().toString();
        user.image = setting.getString("ID", "");
        user.username = "fofo";
        API api = RestAdapter.createAPI();
        Call<CallbackJoin> callbackCall = api.shopuserupdate(user);

        callbackCall.enqueue(new Callback<CallbackJoin>() {
            @Override
            public void onResponse(Call<CallbackJoin> call, Response<CallbackJoin> response) {
                CallbackJoin resp = response.body();
                if (resp != null && resp.status.equals("success")) {
//                    Toast.makeText(JoinShopInfoActivity.this, "회원가입 완료"+resp.data.id, Toast.LENGTH_SHORT).show();
                    //  sharedPref.setOpenAppCounter(0);
                    setting = getSharedPreferences("setting", 0);

                    editor= setting.edit();


                    editor.putString("addr", member_addr.getText().toString());
                    editor.commit();

                    finish();
                }
            }

            @Override
            public void onFailure(Call<CallbackJoin> call, Throwable t) {

                Log.e("onFailure", t.getMessage());
            }
        });
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cerificaton_check:
                submitOrderData();
//                initCheck();
//                if (certification.equals(member_cerificaton.getText().toString())) {
//                    Toast.makeText(this, "일치", Toast.LENGTH_SHORT).show();
//                    initCheck();
//                } else {
//                    Toast.makeText(this, "불일치", Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }
}