package com.example.example6_19;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;

public class MyTabFragment extends Fragment {

    String tabName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 여기에서는 탭이름이 '음악별, 가수별, 앨범별'로 지정된다.
        Bundle data = getArguments();
        tabName = data.getString("tabName");
    }

    @Override
    // 프래그먼트에 나타날 화면을 구성한다. 최종적으로 구성한 화면을 return 한다.
    // 인플레이션(inflation)이란 레이아웃xml에 명시한 위젯이
    // 안드로이드 메모리에 셩성되는 과정입니다.
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 빈 리니어레이아웃을 생성한다.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        LinearLayout baseLayout = new LinearLayout(super.getActivity());
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setLayoutParams(params);

        // 각 탭별로 배경색을 다르게 설정한다.
        if (tabName == "음악별")
            baseLayout.setBackgroundColor(Color.RED);
        if (tabName == "가수별")
            baseLayout.setBackgroundColor(Color.GREEN);
        if (tabName == "앨범별")
            baseLayout.setBackgroundColor(Color.BLUE);

        // 구성한 레이아웃을 반환한다. 이 레이아웃이 액션바의 아래쪽에 나타나게 된다.
        return baseLayout;
    }
}
