package me.onulhalin.sample.onul_shop.base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import me.onulhalin.fragmentation.SupportFragment;
import me.onulhalin.sample.R;

/**
 *
 */
public class BaseBackFragment extends SupportFragment {

  protected void initToolbarNav(Toolbar toolbar) {
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        _mActivity.onBackPressed();
      }
    });
  }
}
