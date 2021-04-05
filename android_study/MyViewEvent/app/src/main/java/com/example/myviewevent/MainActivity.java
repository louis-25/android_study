package com.example.myviewevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

class MyView extends View {
    int key;
    String str;
    int x,y;

    public MyView(Context context) {
        super(context);
        setBackgroundColor(Color.YELLOW);
    }
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX(0);
        y = (int) event.getY(0);
        invalidate();
        return super.onTouchEvent(event);
    }
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(60);
        canvas.drawText("("+x+","+y+")에서 터치 이벤트가 발생하였음",x,y,paint);
    }
}

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyView w = new MyView(this);
        setContentView(w);
    }
}
