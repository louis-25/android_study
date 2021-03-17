package me.onulhalin.sample.onul_shop.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackCerification;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackJoin;
import me.onulhalin.sample.onul_shop.model.login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DebugActivity extends OHActivitiy implements View.OnClickListener {


    EditText member_id,member_pw,member_repw,member_email,member_name,member_phone,member_cerificaton;
    Button cerificaton_check, send;
    TextView repwcheck;
    private String certification="null";
    private Call<CallbackCerification> callbackCall = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailmember);
        initview();
        initData();
    }


    private void initview(){
        member_id = (EditText)findViewById(R.id.member_id);
        member_pw =(EditText)findViewById(R.id.member_pw);
        member_repw =(EditText) findViewById(R.id.member_repw);
        member_email = (EditText)findViewById(R.id.member_email);
        member_name = (EditText)findViewById(R.id.member_name);
        member_phone = (EditText)findViewById(R.id.member_phone);
        member_cerificaton = (EditText)findViewById(R.id.member_cerificaton);
        repwcheck = (TextView)findViewById(R.id.repwcheck);

        send = (Button)findViewById(R.id.send);
        cerificaton_check = (Button)findViewById(R.id.cerificaton_check);

        send.setOnClickListener(this);
        cerificaton_check.setOnClickListener(this);

        setTitle("이메일 회원가입");
    }

    private void initData(){
        member_repw.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {




        }
            @Override
            public void afterTextChanged(Editable arg0) {
                //member_repw.setText("불일치");
                if(member_pw.getText().toString().equals(member_repw.getText().toString())) {
                    repwcheck.setText("비밀번호가 일치합니다.");
                } else {
                    repwcheck.setText("비밀번호가 일치하지 않습니다.");
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // 입력하기 전에
            }
        });
    }
    private void initCheck(){
//        if (member_id.getText().toString().equals("")){
//            Toast.makeText(this, "ID를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//        }
//        else if (member_pw.getText().toString().equals("")){
//            Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//        }
//        else if (member_repw.getText().toString().equals("")){
//            Toast.makeText(this, "비밀번호 확인을 입력해 주세요.", Toast.LENGTH_SHORT).show();
//        }
//        else  if (member_email.getText().toString().equals("")){
//            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
//        }
//        else  if (member_name.getText().toString().equals("")){
//            Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
//        }
//        else if (member_phone.getText().toString().equals("")){
//            Toast.makeText(this, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//        }
//        else if (member_cerificaton.getText().toString().equals("")){
//            Toast.makeText(this, "인증번호 확인을 입력해 주세요.", Toast.LENGTH_SHORT).show();
//        }
        //else{
            Toast.makeText(this, "다음화면으로", Toast.LENGTH_SHORT).show();
        submitOrderData();
            //모든게 완료..
            //insert 먼저


       // }
    }


    private void submitOrderData() {

        login user = new login();
        user.username = member_id.getText().toString();
        user.password = member_pw.getText().toString();
        user.email = member_email.getText().toString();
        user.name = member_name.getText().toString();
        user.phonenum =member_phone.getText().toString();
        user.chk ="N";



        API api = RestAdapter.createAPI();
        Call<CallbackJoin> callbackCall = api.registerJoin(user);

        callbackCall.enqueue(new Callback<CallbackJoin>() {
            @Override
            public void onResponse(Call<CallbackJoin> call, Response<CallbackJoin> response) {
                CallbackJoin resp = response.body();
                if (resp != null && resp.status.equals("success")) {
                    Toast.makeText(DebugActivity.this, "회원가입 완료"+resp.data.id, Toast.LENGTH_SHORT).show();
                    //  sharedPref.setOpenAppCounter(0);

                    finish();
                }
            }

            @Override
            public void onFailure(Call<CallbackJoin> call, Throwable t) {

                Log.e("onFailure", t.getMessage());
            }
        });
    }




    private void requestNewsInfoDetailsApi(final String data) {
        API api = RestAdapter.createAPI();
        callbackCall = api.getCerification(data);
        callbackCall.enqueue(new Callback<CallbackCerification>() {
            @Override
            public void onResponse(Call<CallbackCerification> call, Response<CallbackCerification> response) {
                CallbackCerification resp = response.body();
                if (resp != null && resp.status.equals("success")) {
                    certification = resp.data;
                    //인증이 완료되고 난뒤에.. 휴대폰번호를 바꿀수도 있는데..?
                    Toast.makeText(DebugActivity.this, "인증번호가 발송되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    
                }
            }

            @Override
            public void onFailure(Call<CallbackCerification> call, Throwable t) {
                Log.e("onFailure", t.getMessage());

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cerificaton_check:
                initCheck();
//                if (certification.equals(member_cerificaton.getText().toString())) {
//                    Toast.makeText(this, "일치", Toast.LENGTH_SHORT).show();
//                    initCheck();
//                } else {
//                    Toast.makeText(this, "불일치", Toast.LENGTH_SHORT).show();
//                }
            break;

            case R.id.send:
                requestNewsInfoDetailsApi("82" + member_phone.getText().toString().substring(1));
                break;
        }
    }
}