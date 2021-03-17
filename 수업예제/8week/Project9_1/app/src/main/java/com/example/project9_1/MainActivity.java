package com.example.project9_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));
    }

    private static class MyGraphicView extends View {
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true); //끝부분 부드럽게 처리
            paint.setColor(Color.BLACK);
            paint.setStrokeCap(Paint.Cap.BUTT); //선이 끝나는 지점의 장식을 설정
            paint.setStrokeWidth(50); //선의 굵기
            canvas.drawLine(50, 30, 600, 30, paint); //(10,10) 에서 (300,10) 까지 선을 그린다 paint는 옵션

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(50); //선의 굵기
            paint.setStrokeCap(Paint.Cap.ROUND); //선이 끝나는 지점의 장식을 설정
            canvas.drawLine(50,100,600,100, paint);

            paint.setColor(Color.BLUE);
            canvas.drawOval(new RectF(50,150,500,400),paint); //타원을 출력한다
            //canvas.drawText("Green Oval",120, 380,paint);

            RectF rect1 = new RectF();
            rect1.set(100,450,500,650);
            canvas.drawArc(rect1,-135,75,true, paint); //부채꼴 출력

            //paint.setStyle(Paint.Style.STROKE);
            paint.setStyle(Paint.Style.FILL); //도형의 내부를 채우는 옵션
            paint.setColor(Color.argb(120,130,150,160));
            Rect rect2 = new Rect(130, 700, 130+100, 700+100); //사각형
            canvas.drawRect(rect2, paint);

            paint.setStyle(Paint.Style.FILL); //도형의 내부를 채우는 옵션
            paint.setColor(Color.argb(120,50,50,160));
            Rect rect3 = new Rect(150, 720, 150+100, 720+100); //사각형
            canvas.drawRect(rect3, paint);

        }
    }
}
