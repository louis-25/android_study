package me.onulhalin.sample.onul_shop.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import me.onulhalin.sample.R;

/**
 *
 */

public class OHActivitiy extends AppCompatActivity {

    private ViewGroup m_contentView = null;
  //  private PHProgressDialog progressDialog;

    @Override
    protected void onResume() {
        super.onResume();
       // App.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
       // clearReferences();
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        View back = findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    overridePendingTransition(0, 0);
                }
            });
        }

    }

    public void setTitle(String title){
        TextView titleTV = (TextView) findViewById(R.id.title);
        if (titleTV != null) {
            titleTV.setText(title);
        }
    }

    public void setTitle(int titleId) {
        TextView titleTV = (TextView) findViewById(R.id.title);
        if (titleTV != null) {
            titleTV.setText(titleId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  clearReferences();
        nullViewDrawablesRecursive(m_contentView);
        m_contentView = null;
//        System.gc();
    }

//    private void clearReferences() {
//        Activity currActivity = PHApplication.getCurrentActivity();
//        if (currActivity != null && currActivity.equals(this)) {
//            PHApplication.setCurrentActivity(null);
//        }
//    }

    @Override
    public void setContentView(int layoutResID) {
        try {
            m_contentView = (ViewGroup) LayoutInflater.from(this).inflate(layoutResID, null);
            setContentView(m_contentView);
        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(getApplicationContext(), getString(R.string.temp_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        m_contentView = (ViewGroup) view;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void nullViewDrawablesRecursive(View view) {
        if (view != null) {
            try {
                ViewGroup viewGroup = (ViewGroup) view;

                int childCount = viewGroup.getChildCount();
                for (int index = 0; index < childCount; index++) {
                    View child = viewGroup.getChildAt(index);
                    nullViewDrawablesRecursive(child);
                }
            } catch (Exception e) {
            }

            nullViewDrawable(view);
        }
    }

    @SuppressLint("NewApi")
    private void nullViewDrawable(View view) {
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                view.setBackground(null);
            } else {
                view.setBackgroundDrawable(null);
            }
        } catch (Exception e) {
        }

        try {
            ImageView imageView = (ImageView) view;
            imageView.setImageDrawable(null);
            if (Build.VERSION.SDK_INT >= 16) {
                imageView.setBackground(null);
            } else {
                imageView.setBackgroundDrawable(null);
            }
        } catch (Exception e) {
        }
    }


//    public void setUIToWait(boolean wait) {
////
////        if (wait) {
////            progressDialog = PHProgressDialog.show(this);
////        } else {
////            if(progressDialog != null && progressDialog.isShowing()){
////                progressDialog.close();
////            }
////        }
////
////    }
}
