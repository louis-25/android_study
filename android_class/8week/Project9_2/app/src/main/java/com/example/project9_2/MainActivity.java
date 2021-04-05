package com.example.project9_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    ImageButton ibZoomin, ibZoomout, ibRotate, ibBright, ibDark, ibGray;
    static float scaleX=1, scaleY=1, angle=0, color=1, satur=1;
    MyGraphicView graphicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("미니 포토샵");

        LinearLayout pictureLayout = findViewById(R.id.pictureLayout);
        graphicView = new MyGraphicView(this);
        pictureLayout.addView(graphicView);

        clickIcons();

    }

    private void clickIcons() {
        ibZoomin = findViewById(R.id.ibZoomin);
        ibRotate = findViewById(R.id.ibRotate);
        ibBright = findViewById(R.id.ibBright);
        ibZoomout = findViewById(R.id.ibZoomout);
        ibDark = findViewById(R.id.ibDark);
        ibGray = findViewById(R.id.ibGray);

        ibZoomin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                scaleX = scaleX + 0.2f;
                scaleY = scaleY + 0.2f;
                graphicView.invalidate(); // onDraw호출
            }
        });
        ibZoomout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                scaleX = scaleX - 0.2f;
                scaleY = scaleY - 0.2f;
                graphicView.invalidate(); // onDraw호출
            }
        });
        ibRotate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                angle = angle + 20;
                graphicView.invalidate(); // onDraw호출
            }
        });
        ibBright.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                color = color + 0.2f;
                graphicView.invalidate(); // onDraw호출
            }
        });
        ibDark.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                color = color - 0.2f;
                graphicView.invalidate(); // onDraw호출
            }
        });
        ibGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(satur == 0) satur = 1;
                else satur = 0;
                graphicView.invalidate(); // onDraw호출
            }
        });
    }

    private static class MyGraphicView extends View {
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Bitmap picture = BitmapFactory.decodeResource(getResources(),R.drawable.lena256);
            int picX = (this.getWidth() - picture.getWidth()) / 2;
            int picY = (this.getHeight() - picture.getHeight()) / 2;

            int cenX = this.getWidth() / 2;
            int cenY = this.getHeight() / 2;

            Paint paint = new Paint();
            float[] array = {color, 0, 0, 0, 0,
                            0, color, 0, 0, 0,
                            0, 0, color, 0, 0,
                            0, 0, 0, 1, 0};
            ColorMatrix cm = new ColorMatrix(array);
            if(satur==0) cm.setSaturation(satur);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));

            canvas.scale(scaleX, scaleY, cenX, cenY);
            canvas.rotate(angle, cenX, cenY);
            canvas.drawBitmap(picture,picX,picY,paint);

            picture.recycle();
        }
    }
}
