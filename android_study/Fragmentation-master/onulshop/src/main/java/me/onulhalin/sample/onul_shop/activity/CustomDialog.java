package me.onulhalin.sample.onul_shop.activity;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
        public CustomDialog(Context context) {
            this.context = context;
        }

        // 호출할 다이얼로그 함수를 정의한다.
        public void callFunction(String serialKey) {

            // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
            final Dialog dlg = new Dialog(context);
            Key = serialKey;
            // 액티비티의 타이틀바를 숨긴다.
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // 커스텀 다이얼로그의 레이아웃을 설정한다.
            dlg.setContentView(R.layout.dialog_custom);
            final Button okButton = (Button) dlg.findViewById(R.id.okButton);
            okButton.setOnClickListener(this);
            // 커스텀 다이얼로그를 노출한다.
            dlg.show();

            // 커스텀 다이얼로그의 각 위젯들을 정의한다.

        }




    private void submitOrderData() {


        Order order = new Order();

        order.uid = Key;
        order.cancel = "app결제 취소 테스트";
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
                submitOrderData();
                Toast.makeText(context, ""+Key, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}