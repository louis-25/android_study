package me.onulhalin.sample.onul_shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import me.onulhalin.fragmentation.SupportActivity;
import me.onulhalin.fragmentation.SupportFragment;
import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.activity.ApplyActivity;
import me.onulhalin.sample.onul_shop.base.BaseMainFragment;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackJoin;
import me.onulhalin.sample.onul_shop.model.login;
import me.onulhalin.sample.onul_shop.ui.fragment.second.ZhihuSecondFragment;
import me.onulhalin.sample.onul_shop.ui.fragment.second.child.ViewPagerFragment;
import me.onulhalin.sample.onul_shop.ui.view.BottomBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import me.onulhalin.eventbusactivityscope.EventBusActivityScope;


/**
 *
 */
public class MainActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    SharedPreferences setting;
    private SupportFragment[] mFragments = new SupportFragment[4];
    private Button bottom;
    private DrawerLayout drawerLayout;
    private BottomBar mBottomBar;
    private TextView header_next;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_activity_main);
        bottom =(Button) findViewById(R.id.bottom);
        TextView a= (TextView)findViewById(R.id.etc1);
        header_next = (TextView)findViewById(R.id.header_next);

//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
////        boolean isWhiteListing = false;
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
////            isWhiteListing = pm.isIgnoringBatteryOptimizations(getBaseContext().getPackageName());
////        }
////
////
////        if(!isWhiteListing){
////            AlertDialog.Builder setdialog = new AlertDialog.Builder(MainActivity.this);
////            setdialog.setTitle("권한이 필요합니다.")
////                    .setMessage("어플을 사용하기 위해서는 해당 어플을 \"배터리 사용량 최적화\" 목록에서 제외하는 권한이 필요합니다. 계속하시겠습니까?")
////                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            Intent intent  = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
////                            intent.setData(Uri.parse("package:"+ getBaseContext().getPackageName()));
////                            getBaseContext().startActivity(intent);
////                        }
////                    })
////                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            Toast.makeText(MainActivity.this, "권한 설정을 취소했습니다.", Toast.LENGTH_SHORT).show();
////                        }
////                    })
////                    .create()
////                    .show();
////        }


//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Intent intent = new Intent();
//            String packageName = getPackageName();
//            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
//            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + packageName));
//                startActivity(intent);
//            }
//        }


        setting = getSharedPreferences("setting", 0);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(MainActivity.this, "aaaa", Toast.LENGTH_SHORT).show();
            }
        });
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ApplyActivity.class);
                //i.putExtra("name",data.get(arg2).products.get(arg2).id);
              //  i.putExtra("name1","flase");
                startActivity(i);

            }
        });

        SupportFragment firstFragment = null;
            if (firstFragment == null) {

            mFragments[SECOND] = ZhihuSecondFragment.newInstance();


            loadMultipleRootFragment(R.id.fl_container, FIRST,

                    mFragments[SECOND]);

        } else {

            mFragments[SECOND] = findFragment(ZhihuSecondFragment.class);

        }

        initView();

        header_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopUserCommute();
            }
        });
    }







    private void shopUserCommute() {

        login user = new login();
        user.username = setting.getString("ID", "");
        //n 퇴근 y출근
        user.commute = "N";
        API api = RestAdapter.createAPI();
        Call<CallbackJoin> callbackCall = api.shopUserCommute(user);

        callbackCall.enqueue(new Callback<CallbackJoin>() {
            @Override
            public void onResponse(Call<CallbackJoin> call, Response<CallbackJoin> response) {
                CallbackJoin resp = response.body();
                if (resp ==null){
                    // submitLoginData();
                }
                if (resp != null && resp.status.equals("success")) {
                    Toast.makeText(MainActivity.this, "오늘도 수고하셨습니다.", Toast.LENGTH_SHORT).show();
                    //api.. 추가해야함..
                    //preferences 지우기
                  //  SharedPreferences pref = getSharedPreferences("setting", 0);
                  //  SharedPreferences.Editor editor = pref.edit();
                //    editor.remove("ID");
                 //   editor.commit();
                 //   finish();
                }

            }

            @Override
            public void onFailure(Call<CallbackJoin> call, Throwable t) {

                Log.e("onFailure", t.getMessage());
            }
        });
    }



    private void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);



        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        final ScrollView navigationLL = (ScrollView) findViewById(R.id.navigation);
        findViewById(R.id.hamberger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationLL)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });


        mBottomBar.setVerticalGravity(View.GONE);
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                final SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();


                if (count > 1) {
                   if (currentFragment instanceof ZhihuSecondFragment) {
                        currentFragment.popToChild(ViewPagerFragment.class, false);
                    }
//
                    return;
                }

                if (count == 1) {

        //            EventBusActivityScope.getDefault(MainActivity.this).post(new TabSelectedEvent(position));
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void onBackToFirstFragment() {
        finish();
        Log.d("kimtest","@@@!!!!");
        mBottomBar.setCurrentItem(0);
    }


}