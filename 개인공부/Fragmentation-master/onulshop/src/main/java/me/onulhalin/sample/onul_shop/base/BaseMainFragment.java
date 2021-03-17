package me.onulhalin.sample.onul_shop.base;

import android.content.Context;

import me.onulhalin.fragmentation.SupportFragment;


/**
 *
 *
 */
public abstract class BaseMainFragment extends SupportFragment {
  protected OnBackToFirstListener _mBackToFirstListener;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnBackToFirstListener) {
      _mBackToFirstListener = (OnBackToFirstListener) context;
    } else {
      throw new RuntimeException(context.toString()
              + " must implement OnBackToFirstListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    _mBackToFirstListener = null;
  }


  @Override
  public boolean onBackPressedSupport() {
    if (getChildFragmentManager().getBackStackEntryCount() > 1) {
      popChild();
    } else {
      if (this instanceof BaseMainFragment) {
        _mActivity.finish();
      } else {
        _mBackToFirstListener.onBackToFirstFragment();
      }
    }
    return true;
  }

  public interface OnBackToFirstListener {
    void onBackToFirstFragment();
  }
}
