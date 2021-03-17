package me.onulhalin.sample.onul_shop.ui.fragment.second.child;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.onulhalin.fragmentation.SupportFragment;
import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.adapter.ZhihuPagerFragmentAdapter;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackCnt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 */
public class ViewPagerFragment extends SupportFragment {
    private TabLayout mTab;
    private ViewPager mViewPager;
    private String aa ="0";
    SharedPreferences setting;
    public static ViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_fragment_second_pager, container, false);
        setting = getContext().getSharedPreferences("setting", 0);
        submitData(setting.getString("ID", ""));
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        submitData(setting.getString("ID", ""));
    }


    private void submitData(String id) {
        API api = RestAdapter.createAPI();
        Call<CallbackCnt> callbackCall = api.getStatusCnt(id);

        callbackCall.enqueue(new Callback<CallbackCnt>() {
            @Override
            public void onResponse(Call<CallbackCnt> call, Response<CallbackCnt> response) {
                CallbackCnt resp = response.body();
                if (resp != null) {
                    if (resp.status.equals("success")){
                       // Toast.makeText(_mActivity, "bbbb"+resp.status1.count, Toast.LENGTH_SHORT).show();
                        aa= resp.status1.count;
                        mTab.addTab(mTab.newTab());
                        mTab.addTab(mTab.newTab());
                        mTab.addTab(mTab.newTab());

                        mViewPager.setAdapter(new ZhihuPagerFragmentAdapter(getContext(),getChildFragmentManager(),
                                "접수대기("+aa+")", "처리중 ("+resp.status1.count1+")", "주문완료"));
                        mTab.setupWithViewPager(mViewPager);

                    }
                }

            }
            @Override
            public void onFailure(Call<CallbackCnt> call, Throwable t) {

            }
        });
    }


    private void initView(View view) {
        mTab = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);




        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition()==0) {

                }
                else if (tab.getPosition()==1){

                }
                else if (tab.getPosition()==2){

                }
                }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
