package me.onulhalin.sample.onul_shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends OHActivitiy  implements View.OnClickListener {


    TextView order_name,order_phone,order_address,order_pickuptime;
    Button accept , cancel;
    Intent intent;
    String serialKey ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        intent = getIntent();

        Toast.makeText(this, ""+intent.getStringExtra("position"), Toast.LENGTH_SHORT).show();

        initView();
//        initData();

        submitOrderDetailData();

    }

    private void initView(){
        setTitle("주문 상세 정보");
        order_name = (TextView)findViewById(R.id.order_name);
        order_phone = (TextView)findViewById(R.id.order_phone);
        order_address = (TextView)findViewById(R.id.order_address);
        order_pickuptime = (TextView)findViewById(R.id.order_pickuptime);
        cancel = (Button)findViewById(R.id.cancel);
        accept = (Button)findViewById(R.id.accept);
        cancel.setOnClickListener(this);
        accept.setOnClickListener(this);
    }

    private void submitOrderDetailData() {
        API api = RestAdapter.createAPI();
        Call<CallbackOrder> callbackCall = api.getOneProductOrder(intent.getStringExtra("position"));

        callbackCall.enqueue(new Callback<CallbackOrder>() {
            @Override
            public void onResponse(Call<CallbackOrder> call, Response<CallbackOrder> response) {
                CallbackOrder resp = response.body();
                if (resp != null) {
                    order_name.setText(resp.order_detail.buyer);
                    order_phone.setText(resp.order_detail.phone);
                    order_address.setText(resp.order_detail.address);
                    serialKey = resp.order_detail.serial;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                CustomDialog customDialog = new CustomDialog(DetailActivity.this);
                customDialog.callFunction(serialKey);

                break;
        }
    }
}