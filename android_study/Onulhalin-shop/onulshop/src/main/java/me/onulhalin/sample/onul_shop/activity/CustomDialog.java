package me.onulhalin.sample.onul_shop.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrderCancel;
import me.onulhalin.sample.onul_shop.model.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialog implements View.OnClickListener {

        private Context context;
        private Button a,b;
        private String Key;
        private RadioButton cb_cc_firstoder,cb_cc_userinfo,aa;
        private String cancelReason;
        EditText directly_input;
     Dialog dlg;
        public CustomDialog(Context context) {
            this.context = context;
        }

        // 호출할 다이얼로그 함수를 정의한다.
        public void callFunction(String serialKey) {

            // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
            dlg = new Dialog(context);
            Key = serialKey;
            // 액티비티의 타이틀바를 숨긴다.
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // 커스텀 다이얼로그의 레이아웃을 설정한다.
            dlg.setContentView(R.layout.dialog_custom);
            cb_cc_userinfo =(RadioButton)dlg.findViewById(R.id.cb_cc_userinfo);
            cb_cc_firstoder = (RadioButton)dlg.findViewById(R.id.cb_cc_firstoder);
            directly_input = (EditText)dlg.findViewById(R.id.directly_input);
            directly_input.setVisibility(View.GONE);
            aa= (RadioButton)dlg.findViewById(R.id.aa);
            final Button okButton = (Button) dlg.findViewById(R.id.okButton);
            okButton.setOnClickListener(this);
            aa.setOnClickListener(optionOnClickListener);
            cb_cc_userinfo.setOnClickListener(optionOnClickListener);
            cb_cc_firstoder.setOnClickListener(optionOnClickListener);





            // 커스텀 다이얼로그를 노출한다.
            dlg.show();

            // 커스텀 다이얼로그의 각 위젯들을 정의한다.

        }

    RadioButton.OnClickListener optionOnClickListener
            = new RadioButton.OnClickListener() {

        public void onClick(View v) {
            if (aa.isChecked()){
                directly_input.setVisibility(View.VISIBLE);

            }
            else if (cb_cc_firstoder.isChecked()){
                directly_input.setVisibility(View.GONE);
                cancelReason = "먼저 결제된 건으로 해당구매가 어렵습니다.";
            }
            else if (cb_cc_userinfo.isChecked()){
                directly_input.setVisibility(View.GONE);
                cancelReason = "고객님의 정보가 부족합니다.";
            }


//            directly_input.setFocusable(false);
//            directly_input.setFocusableInTouchMode(false);
           Log.d("test", "Option 1 : " + cb_cc_userinfo.isChecked() + "\n" + "Option 2 : " + cb_cc_firstoder.isChecked() + "\n");

        }
    };



    private void submitOrderData() {


        Order order = new Order();

        order.uid = Key;
        order.cancel = cancelReason;
        API api = RestAdapter.createAPI();
        Call<CallbackOrderCancel> callbackCall = api.cancelOrder(order);

        callbackCall.enqueue(new Callback<CallbackOrderCancel>() {
            @Override
            public void onResponse(Call<CallbackOrderCancel> call, Response<CallbackOrderCancel> response) {
                CallbackOrderCancel resp = response.body();
                if (resp != null && resp.code.equals("0")) {
                    //    Log.d("kimtest","@@" +resp.user.name);
                    //  sharedPref.setOpenAppCounter(0);
                    Toast.makeText(context, "취소 성공", Toast.LENGTH_SHORT).show();

                    dlg.dismiss();
                    ((Activity)context).finish();
                }
                else {
                    Toast.makeText(context, "취소실패", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CallbackOrderCancel> call, Throwable t) {
              //  Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.okButton:

                if (!aa.isChecked() && !cb_cc_firstoder.isChecked() && !cb_cc_userinfo.isChecked()){
                    Toast.makeText(context, "취소 사유를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if (aa.isChecked()) {
                        cancelReason = directly_input.getText().toString();
                    }
                    submitOrderData();
                }

                break;
        }
    }
}