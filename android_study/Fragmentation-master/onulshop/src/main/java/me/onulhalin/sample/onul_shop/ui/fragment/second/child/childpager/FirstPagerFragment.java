package me.onulhalin.sample.onul_shop.ui.fragment.second.child.childpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.onulhalin.eventbusactivityscope.EventBusActivityScope;
import me.onulhalin.fragmentation.SupportFragment;
import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.MainActivity;
import me.onulhalin.sample.onul_shop.activity.DetailActivity;
import me.onulhalin.sample.onul_shop.adapter.HomeAdapter;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrder;
import me.onulhalin.sample.onul_shop.entity.Article;
import me.onulhalin.sample.onul_shop.event.TabSelectedEvent;
import me.onulhalin.sample.onul_shop.listener.OnItemClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class FirstPagerFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;
    private HomeAdapter mAdapter;
    private boolean mAtTop = true;
    private int mScrollTotal;
    List<Article> articleList = new ArrayList<>();
    private static final String ARG_TYPE = "arg_type";
    Intent intent;
    public static FirstPagerFragment newInstance(String userid,int status) {

        Bundle args = new Bundle();

        args.putString(ARG_TYPE, String.valueOf(status));
        FirstPagerFragment fragment = new FirstPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_fragment_second_pager_first, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        submitData();

    }

    private void initView(View view) {
        //Toast.makeText(_mActivity, "aaaa"+getArguments().getString(ARG_TYPE), Toast.LENGTH_SHORT).show();
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);


        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);


        mAdapter = new HomeAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);

        //submitOrderData();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {

                intent = new Intent(getContext(),DetailActivity.class);
                intent.putExtra("position",mAdapter.getItem(position).getId());
                Toast.makeText(getContext(), ""+mAdapter.getItem(position).getId(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mAtTop = true;
                } else {
                    mAtTop = false;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
                Toast.makeText(_mActivity, "리프"+getArguments().getString(ARG_TYPE), Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }


    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {

        if (event.position != MainActivity.SECOND) return;

        if (mAtTop) {
            mRefreshLayout.setRefreshing(true);

            onRefresh();
        } else {
            scrollToTop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }


    private void submitData() {
        API api = RestAdapter.createAPI();
        Call<CallbackOrder> callbackCall = api.getAllProductOrder(getArguments().getString(ARG_TYPE),"fofo");

        callbackCall.enqueue(new Callback<CallbackOrder>() {
            @Override
            public void onResponse(Call<CallbackOrder> call, Response<CallbackOrder> response) {
                CallbackOrder resp = response.body();
                if (resp != null) {

                    for (int i =0; i<resp.order.size(); i++) {
                        Log.d("kimtest","@@"+resp.order.size());
                        Article article = new Article(resp.order.get(i).name, String.valueOf(resp.order.get(i).buyer),resp.order.get(i).created_at,resp.order.get(i).id);
                        Log.d("kimtest","@@???"+resp.order.get(i).created_at);
                        articleList.add(article);
                    }

                    mAdapter.notifyDataSetChanged();

                    mAdapter.setDatas(articleList);
                    mRecy.setAdapter(mAdapter);
                    articleList.clear();
                }

            }

            @Override
            public void onFailure(Call<CallbackOrder> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });
    }

}