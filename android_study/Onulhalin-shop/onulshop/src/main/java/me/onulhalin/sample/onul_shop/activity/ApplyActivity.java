package me.onulhalin.sample.onul_shop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.adapter.CustomAdapter;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackProduct;
import me.onulhalin.sample.onul_shop.data.Dictionary;
import me.onulhalin.sample.onul_shop.listener.OnItemClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyActivity  extends OHActivitiy implements View.OnClickListener {
    Button addBtn;

    private static String TAG = "recyclerview_example";

    private ArrayList<Dictionary> mArrayList;
    private CustomAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private int count = -1;
    Intent intent;
    SharedPreferences setting;
    private int draft=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        initview();
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        setting = getSharedPreferences("setting", 0);
        // MainActivity에서 RecyclerView의 데이터에 접근시 사용됩니다.
        mArrayList = new ArrayList<>();



        // RecyclerView를 위해 CustomAdapter를 사용합니다.
        mAdapter = new CustomAdapter( mArrayList,getApplicationContext(),"aa");


        // RecyclerView의 줄(row) 사이에 수평선을 넣기위해 사용됩니다.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {

//
                intent = new Intent(getApplicationContext(),NumberPickerActivity.class);
                intent.putExtra("stock",mAdapter.getItem(position).getKorean());
                intent.putExtra("position",mAdapter.getItem(position).getId());
                intent.putExtra("draft",mAdapter.getItem(position).getMdraft());

                startActivityForResult(intent,3000);

//                Intent intent = new Intent(getApplicationContext(), NumberPickerActivity.class);
//                intent.putExtra("position",position);
//                startActivity(intent);




            }
        });

        mRecyclerView.setAdapter(mAdapter);


        Button buttonInsert = (Button)findViewById(R.id.addBtn);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), ProductAddActivity.class);
                //i.putExtra("name",data.get(arg2).products.get(arg2).id);
                i.putExtra("name1","flase");
                startActivity(i);


            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        ProductData();
    }


    private void ProductData() {
        API api = RestAdapter.createAPI();
        Call<CallbackProduct> callbackCall = api.getAllListProduct(setting.getString("ID", ""));

        callbackCall.enqueue(new Callback<CallbackProduct>() {
            @Override
            public void onResponse(Call<CallbackProduct> call, Response<CallbackProduct> response) {
                CallbackProduct resp = response.body();
                if (resp != null) {
                        mArrayList.clear();
                    for (int i =0; i<resp.products.size(); i++) {
                        //   Article article = new Article(resp.order.get(i).name, String.valueOf(resp.order.get(i).buyer),resp.order.get(i).created_at);
                        Dictionary dict = new Dictionary(resp.products.get(i).id+"", "" + resp.products.get(i).name, "" + resp.products.get(i).stock,resp.products.get(i).image, ""+resp.products.get(i).draft);//, resp.products.get(i).draft);
                        mArrayList.add(dict); // RecyclerView의 마지막 줄에 삽입
                    }


                    mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영


                }

            }

            @Override
            public void onFailure(Call<CallbackProduct> call, Throwable t) {
                Toast.makeText(getBaseContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });
    }


    private void initview(){

        setTitle("오늘의 메뉴 적용");
        //  addBtn=(Button)findViewById(R.id.addBtn);
        // addBtn.setOnClickListener(this);
    }

    private void initData(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.addBtn:
//                Intent i = new Intent(getBaseContext(), ProductAddActivity.class);
//                //i.putExtra("name",data.get(arg2).products.get(arg2).id);
//                i.putExtra("name1","flase");
//                startActivity(i);
//                break;
        }
    }



    private void submitData(int id, int stock, final int draft) {
        API api = RestAdapter.createAPI();
        Call<CallbackProduct> callbackCall = api.setUpdateStock(id,stock,draft);

        callbackCall.enqueue(new Callback<CallbackProduct>() {
            @Override
            public void onResponse(Call<CallbackProduct> call, Response<CallbackProduct> response) {
                CallbackProduct resp = response.body();
                if (resp != null) {
                    if (resp.status.equals("success")){
                       // Toast.makeText(getApplicationContext(), "qtqt"+draft, Toast.LENGTH_SHORT).show();
                        Toast.makeText(ApplyActivity.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                        mArrayList.clear();
                        ProductData();
                    }
                }

            }

            @Override
            public void onFailure(Call<CallbackProduct> call, Throwable t) {
                Toast.makeText(getBaseContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
// MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 3000:

                    //mAdapter.setText(1, 5);



                    if (data.getStringExtra("draft").equals("true")){
                        draft =1;

                    }
                    else {
                        draft=0;

                    }
                    submitData(Integer.valueOf(data.getStringExtra("result")),Integer.valueOf(data.getStringExtra("stock")),draft );
//                    mainResultTv.setText(data.getStringExtra("result"));
                    break;
            }
        }
    }


}
