package com.example.example13_2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar1;
    TextView tvSeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("직접풀어보기 13-2");

        seekBar1 = findViewById(R.id.seekBar1);
        tvSeek = findViewById(R.id.tvSeek);

        final Switch aSwitch = (Switch) findViewById(R.id.switch1);
        final MediaPlayer mPlayer;
        mPlayer = MediaPlayer.create(this, R.raw.song1);

        seekBar1.setMax(mPlayer.getDuration()); // 음악의 총 길이를 시크바 최대값에 적용

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(aSwitch.isChecked() == true){
                    mPlayer.start();
                    new Thread() {  // 쓰레드 생성
                        @Override
                        public void run() {
                            while (mPlayer.isPlaying()) {  // 음악이 실행중일때 계속 돌아가게 함
                                try {
                                    Thread.sleep(1000); // 1초마다 시크바 움직이게 함
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                // 현재 재생중인 위치를 가져와 시크바에 적용
                                seekBar1.setProgress(mPlayer.getCurrentPosition());
                            }
                        }
                    }.start();
                }
                else {
                    mPlayer.stop();
                    try {
                        mPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeek.setText("진행률 : "+(int)((float)progress/(float)seekBar.getMax()*100)+" %");

                if(fromUser){ //사용자에 의해 움직였을때
                    mPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
