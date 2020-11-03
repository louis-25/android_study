package com.example.exam_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();
        Object tabSpec = tabHost.TabSpec("과일");
        Object tabSpec = tabHost.TabSpec("채소");
        Object tabSpec = tabHost.TabSpec("구매한내역");
    }
}
