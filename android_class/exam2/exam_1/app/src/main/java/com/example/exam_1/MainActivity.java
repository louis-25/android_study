package com.example.exam_1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("SameImageSelectingGame");

        final GridView gv = findViewById(R.id.gridView1);
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);


    }
    public class MyGridAdapter extends BaseAdapter{


        Context context;
        Integer[] posterID = {
                R.drawable.apple, R.drawable.banana, R.drawable.flower,
                R.drawable.flag, R.drawable.chicken, R.drawable.car,
                R.drawable.candy, R.drawable.tiger,R.drawable.apple, R.drawable.banana, R.drawable.flower,
                R.drawable.flag, R.drawable.chicken, R.drawable.car,
                R.drawable.candy, R.drawable.tiger,
        };
        Integer basic_image = R.drawable.android_background;
        public MyGridAdapter(Context C){
            context = C;
        }
        @Override
        public int getCount() {
            return posterID.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        int temp = 0;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new GridView.LayoutParams(200,300));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5,5,5,5);

            imageview.setImageResource(basic_image);


            final int pos = position;

            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageview.setImageResource(posterID[pos]);
                }
            });
            return imageview;
        }


    }


}