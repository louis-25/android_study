package me.onulhalin.sample.onul_shop.ui.fragment.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.base.BaseMainFragment;
import me.onulhalin.sample.onul_shop.ui.fragment.second.child.ViewPagerFragment;

/**
 *
 */
public class ZhihuSecondFragment extends BaseMainFragment {

    public static ZhihuSecondFragment newInstance() {

        Bundle args = new Bundle();

        ZhihuSecondFragment fragment = new ZhihuSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_fragment_second, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(ViewPagerFragment.class) == null) {
            loadRootFragment(R.id.fl_second_container, ViewPagerFragment.newInstance());
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }
}
