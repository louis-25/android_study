package me.onulhalin.sample.onul_shop.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface OnItemClickListener {
  void onItemClick(int position, View view, RecyclerView.ViewHolder vh);
}