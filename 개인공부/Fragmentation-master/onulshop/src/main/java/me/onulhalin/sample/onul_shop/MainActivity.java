package me.onulhalin.sample.onul_shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import me.onulhalin.eventbusactivityscope.EventBusActivityScope;
import me.onulhalin.fragmentation.SupportActivity;
import me.onulhalin.fragmentation.SupportFragment;
import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.activity.ApplyActivity;
import me.onulhalin.sample.onul_shop.base.BaseMainFragment;
import me.onulhalin.sample.onul_shop.event.TabSelectedEvent;

import me.onulhalin.sample.onul_shop.ui.fragment.second.ZhihuSecondFragment;
import me.onulhalin.sample.onul_shop.ui.fragment.second.child.ViewPagerFragment;

import me.onulhalin.sample.onul_shop.ui.view.BottomBar;


/**
 * 类知乎 复杂嵌套Demo tip: 多使用右上角的"查看栈视图"
 * Created by YoKeyword on 16/6/2.
 */
public class MainActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];
    private Button bottom;
    private DrawerLayout drawerLayout;
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_activity_main);
        bottom =(Button) findViewById(R.id.bottom);

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

                    EventBusActivityScope.getDefault(MainActivity.this).post(new TabSelectedEvent(position));
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