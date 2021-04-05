package me.onulhalin.sample.onul_shop.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrder;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrderCancel;
import me.onulhalin.sample.onul_shop.model.Order;
import me.onulhalin.sample.onul_shop.model.ProductOrder;
import me.onulhalin.sample.onul_shop.tool.ToggleButtonGroupTableLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptDialog extends Activity implements View.OnClickListener {

        private Context context;
        private TextView dia_title;
        Intent intent;
        Button sendbtn;
         private String Key;
        String delivery;
        private RadioButton rad1,rad2,rad3,rad4,rad5,rad6;
         ToggleButtonGroupTableLayout aaa;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_accept);

        intent = getIntent();
        Toast.makeText(getApplicationContext(), "==="+intent.getStringExtra("delivery"), Toast.LENGTH_SHORT).show();

        rad1 = (RadioButton)findViewById(R.id.rad1);
        rad2 = (RadioButton)findViewById(R.id.rad2);
        rad3 = (RadioButton)findViewById(R.id.rad3);
        rad4 = (RadioButton)findViewById(R.id.rad4);
        rad5 = (RadioButton)findViewById(R.id.rad5);
        rad6 = (RadioButton)findViewById(R.id.rad6);
        dia_title = (TextView)findViewById(R.id.dia_title);
        aaa = (ToggleButtonGroupTableLayout)findViewById(R.id.radGroup1);


        delivery= intent.getStringExtra("delivery");

        if (delivery.equals("Y")){
            rad1.setText("30분");
            rad2.setText("40분");
            rad3.setText("50분");
            rad4.setText("60분");
            rad5.setText("90분");
            rad6.setText("120분");
            dia_title.setText("배송까지 걸리는\n시간을 선택해 주세요");
        }
        else {
            rad1.setText("즉시");
            rad2.setText("10분");
            rad3.setText("30분");
            rad4.setText("60분");
            rad5.setText("90분");
            rad6.setText("120분");
            dia_title.setText("메뉴가 준비되기까지\n 시간을 선택해 주세요");
        }

        sendbtn = (Button)findViewById(R.id.sendbtn);
        sendbtn.setOnClickListener(this);




            // 커스텀 다이얼로그를 노출한다.

            // 커스텀 다이얼로그의 각 위젯들을 정의한다.

        }




    private void AcceptOrder() {
        API api = RestAdapter.createAPI();
        ProductOrder productOrder =new ProductOrder();
        //productOrder.id = intent.getStringExtra("position");
        productOrder.status="WAITING";
        Call<CallbackOrder> callbackCall = api.AcceptOrder(productOrder);

        callbackCall.enqueue(new Callback<CallbackOrder>() {
            @Override
            public void onResponse(Call<CallbackOrder> call, Response<CallbackOrder> response) {
                CallbackOrder resp = response.body();
                if (resp != null) {
                    if (resp.status.equals("success")){
                        Toast.makeText(context, "주문이 접수되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                    // order_name.setText(resp.order_detail.buyer);
                    //     Log.d("kimtest","@@"+resp.order_detail.buyer);
                }

            }

            @Override
            public void onFailure(Call<CallbackOrder> call, Throwable t) {
                //Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });
    }


    private void submitOrderData() {


        Order order = new Order();

        order.uid = Key;
      //  order.cancel = cancelReason;
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


    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.sendbtn:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",aaa.getCheckedRadioButtonId());
                setResult(RESULT_OK,resultIntent);
                finish();
                break;


        }
    }

}