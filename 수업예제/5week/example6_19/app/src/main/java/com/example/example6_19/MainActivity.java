
import android.graphics.Color;
//import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements ActionBar.TabListener{

    ActionBar.Tab tabSong, tabArtist, tabALbum;

    //프레그먼트 사용하기
    MyTabFragment[] myFrags = new MyTabFragment[3];


    //프레그먼트 클래스 만들기
    public static class MyTabFragment extends Fragment{

        String tabName;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //번들을 이용해서 저장된 현재 상태(=탭이름)를 연결
            Bundle data = getArguments();
            tabName = data.getString("tabName");
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            LinearLayout baseLayout = new LinearLayout(super.getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT );
            baseLayout.setOrientation(LinearLayout.VERTICAL);
            baseLayout.setLayoutParams(params);

            //탭 3개 중, 탭 이름(=tabName)에 따라 (프레그먼트내의)뷰를 생성

            if(tabName == "음악별")
                baseLayout.setBackgroundColor(Color.RED);
            if(tabName == "가수별")
                baseLayout.setBackgroundColor(Color.GREEN);
            if(tabName == "앨범별")
                baseLayout.setBackgroundColor(Color.BLUE);

            //return super.onCreateView(inflater, container, savedInstanceState);
            return baseLayout;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tabSong = bar.newTab();
        tabSong.setText("음악별");
        tabSong.setTabListener(MainActivity.this);
        bar.addTab(tabSong);

        tabArtist = bar.newTab();
        tabArtist.setText("가수별");
        tabArtist.setTabListener(this);
        bar.addTab(tabArtist);

        tabALbum = bar.newTab();
        tabALbum.setText("앨범별");
        tabALbum.setTabListener(this);
        bar.addTab(tabALbum);


//            Button bnt1 = new Button();
//
//            OnClickListner lis = new OnClickListner(){
//                void onclick(View view)
//                {
//                    //이벤트처리
//                }
//            };
//
//            btn1.setOnClickListener(lis  );
//
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        MyTabFragment myTabFrag = null;

        if(myFrags[tab.getPosition()] == null)
        {
            myTabFrag = new MyTabFragment();
            Bundle data = new Bundle();
            data.putString("tabName", tab.getText().toString());

            myTabFrag.setArguments(data);
            myFrags[tab.getPosition()] = myTabFrag;
        }
        else
        {
            myTabFrag = myFrags[tab.getPosition()];
        }

        ft.replace(android.R.id.content ,myTabFrag);

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }