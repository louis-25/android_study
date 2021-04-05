package me.onulhalin.sample.onul_shop.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.onulhalin.sample.onul_shop.ui.fragment.second.child.childpager.FirstPagerFragment;


/**
 *
 */
public class ZhihuPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private Context Context;
    public ZhihuPagerFragmentAdapter(Context context,FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
        Context =context;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return FirstPagerFragment.newInstance("fofo",position+1);

            case 1:

                return FirstPagerFragment.newInstance("fofo",position+1);
            case 2:

                return FirstPagerFragment.newInstance("fofo",position+1);

            default:
                return null;
        }


           // return FirstPagerFragment.newInstance("fofo",position+1);

    }

    @Override
    public int getCount() {
        return mTitles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
