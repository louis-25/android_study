package me.onulhalin.sample.onul_shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrder;
import me.onulhalin.sample.onul_shop.model.ProductOrder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends OHActivitiy  implements View.OnClickListener {


    TextView order_name,order_phone,order_address,order_pickuptime,pd_name,amount,product_price,total_price,totalcount,createat;
    Button accept , cancel,pickupBtn ;
    LinearLayout pickupLL, order_process;
    Intent intent;
    String serialKey ="";
    String mDelivery="";
    LinearLayout delivery_able ;
    String status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        intent = getIntent();

      //  Toast.makeText(this, ""+intent.getStringExtra("position"), Toast.LENGTH_SHORT).show();

        initView();
//        initData();
        submitOrderDetailData();

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void initView(){
        setTitle("주문 상세 정보");
        order_name = (TextView)findViewById(R.id.order_name);
        order_phone = (TextView)findViewById(R.id.order_phone);
        order_address = (TextView)findViewById(R.id.order_address);
        order_pickuptime = (TextView)findViewById(R.id.order_pickuptime);
        product_price = (TextView)findViewById(R.id.product_price);
        total_price = (TextView)findViewById(R.id.total_price);
        totalcount = (TextView)findViewById(R.id.totalcount);
        pd_name = (TextView)findViewById(R.id.pd_name);
        amount = (TextView)findViewById(R.id.amount);
        createat =(TextView)findViewById(R.id.createat);
        cancel = (Button)findViewById(R.id.cancel);
        accept = (Button)findViewById(R.id.accept);
        pickupBtn = (Button)findViewById(R.id.pickupBtn);
        cancel.setOnClickListener(this);
        accept.setOnClickListener(this);
        pickupBtn.setOnClickListener(this);
        order_process = (LinearLayout)findViewById(R.id.order_process);
        pickupLL = (LinearLayout)findViewById(R.id.pickupLL);

        delivery_able = (LinearLayout)findViewById(R.id.delivery_able);


      //  Toast.makeText(this, "~~~"+intent.getStringExtra("type"), Toast.LENGTH_SHORT).show();
       if (intent.getStringExtra("type").equals("3")){
           setTitle("완료 주문 상세 정보");
            cancel.setVisibility(View.GONE);
            accept.setVisibility(View.GONE);
           pickupLL.setVisibility(View.GONE);
           order_process.setVisibility(View.GONE);
        }
       else if (intent.getStringExtra("type").equals("2")){
            cancel.setVisibility(View.VISIBLE);
            accept.setVisibility(View.VISIBLE);
           order_process.setVisibility(View.GONE);
           pickupLL.setVisibility(View.VISIBLE);
        }
        else if (intent.getStringExtra("type").equals("1")){
           order_process.setVisibility(View.VISIBLE);
           pickupLL.setVisibility(View.GONE);
       }
    }

    private void submitOrderDetailData() {
        API api = RestAdapter.createAPI();
      //  Toast.makeText(DetailActivity.this, "!?!?!?!?", Toast.LENGTH_SHORT).show();
        Call<CallbackOrder> callbackCall = api.getOneProductOrder(intent.getStringExtra("position"));

        callbackCall.enqueue(new Callback<CallbackOrder>() {
            @Override
            public void onResponse(Call<CallbackOrder> call, Response<CallbackOrder> response) {
                CallbackOrder resp = response.body();

                if (resp != null) {
                    status= resp.order_detail.status;
                    mDelivery= resp.product_detail.delivery;
                    if (mDelivery.equals("Y")){
                        delivery_able.setVisibility(View.VISIBLE);
                    }
                    else if (mDelivery.equals("N")){
                        delivery_able.setVisibility(View.GONE);
                    }
                    createat.setText(resp.order_detail.created_at);
                        order_name.setText(resp.order_detail.buyer);
                        order_phone.setText("0"+resp.order_detail.phone);
                        order_address.setText(resp.order_detail.address);
                        serialKey = resp.order_detail.serial;
                        order_pickuptime.setText(resp.product_detail.ptstart + "~" + resp.product_detail.ptend);
                        pd_name.setText(resp.product_detail.name);
                        amount.setText("1");
                        product_price.setText(""+resp.product_detail.price_discount);
                        total_price.setText(""+resp.order_detail.total_fees);
                        totalcount.setText(""+resp.order.get(0).amount);


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


    private void AcceptOrder(final String status, String msg) {
        API api = RestAdapter.createAPI();
        ProductOrder productOrder =new ProductOrder();
        productOrder.id = intent.getStringExtra("position");
        productOrder.status=status;
        productOrder.msg = msg;
        Call<CallbackOrder> callbackCall = api.AcceptOrder(productOrder);

        callbackCall.enqueue(new Callback<CallbackOrder>() {
            @Override
            public void onResponse(Call<CallbackOrder> call, Response<CallbackOrder> response) {
                CallbackOrder resp = response.body();
                if (resp != null) {
                    if (resp.status.equals("success")){
                        if (status.equals("WAITING")) {
                            Toast.makeText(DetailActivity.this, "주문이 접수되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equals("STORECONFIRMED")){
                            Toast.makeText(DetailActivity.this, "상품 수령이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        finish();
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


    static void finishActivity(){

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                if (!status.equals("PROCESSED")){
                    Toast.makeText(this, "이미 처리된 주문건 입니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    CustomDialog customDialog = new CustomDialog(DetailActivity.this);
                    customDialog.callFunction(serialKey);
                }

                break;

            case R.id.accept:
                if (!status.equals("PROCESSED")){
                    Toast.makeText(this, "이미 처리된 주문건 입니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), AcceptDialog.class);
                    intent.putExtra("stock", "1");
                    intent.putExtra("delivery", "" + mDelivery);
                    startActivityForResult(intent, 3000);
                }
                //AcceptOrder();
                break;

            case R.id.pickupBtn:
                AcceptOrder("STORECONFIRMED","ss");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
// MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 3000:
                    AcceptOrder("WAITING",data.getStringExtra("result"));
                    Toast.makeText(this, "주문걸리는 시간:"+  data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                    finish();
                    //mAdapter.setText(1, 5);
//                    mainResultTv.setText(data.getStringExtra("result"));
                    break;
            }
        }
    }


}