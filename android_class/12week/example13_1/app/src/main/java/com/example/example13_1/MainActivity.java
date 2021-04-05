package com.example.example13_1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch aSwitch = (Switch) findViewById(R.id.switch1);
        final Button pause_btn = findViewById(R.id.pause_btn);
        final Button stop_btn = findViewById(R.id.stop_btn);

        final MediaPlayer player;
        player = MediaPlayer.create(this, R.raw.song1);

        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pause_btn.getText().toString().equals("일시정지") && aSwitch.isChecked()) {
                    player.pause();
                    pause_btn.setText("이어듣기");
                }
                else if(aSwitch.isChecked()){
                    player.start();
                    pause_btn.setText("일시정지");
                }

            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                try {
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                aSwitch.setChecked(false);
                pause_btn.setText("일시정지");
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked() == true){
                    player.start();
                }
                else {
                    player.stop();
                    try {
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
