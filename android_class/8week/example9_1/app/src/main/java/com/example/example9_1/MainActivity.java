package com.example.example9_1;

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
            paint.setColor(Color.GREEN);
            canvas.drawLine(10, 10, 300, 10, paint); //(10,10) 에서 (300,10) 까지 선을 그린다

            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5); //선의 굵기
            canvas.drawLine(10,30,300,30, paint);

            paint.setColor(Color.RED);
            paint.setStrokeWidth(0);

            paint.setStyle(Paint.Style.FILL); //도형의 내부를 채우는 옵션
            Rect rect1 = new Rect(10, 50, 10+100, 50+100); //사각형
            canvas.drawRect(rect1, paint); //사각형을 paint에 그린다

            paint.setStyle(Paint.Style.STROKE);
            Rect rect2 = new Rect(130, 50, 130+100, 50+100); //사각형
            canvas.drawRect(rect2, paint);

            RectF rect3 = new RectF(250, 50, 250+100, 50+100); //Float형 사각형 만들수 있음
            canvas.drawRoundRect(rect3, 20, 20, paint); //둥근 사각형

            canvas.drawCircle(60,220,50, paint);

            paint.setStrokeWidth(5);

            Path path1 = new Path();
            path1.moveTo(10,290); //커서 이동
            path1.lineTo(10+50, 290+50); //선을 그려줌
            path1.lineTo(10+100, 290);
            path1.lineTo(10+150, 290+50);
            path1.lineTo(10+200, 290);
            canvas.drawPath(path1, paint);

            paint.setStrokeWidth(0);
            paint.setTextSize(30);
            canvas.drawText("안드로이드", 10, 390, paint); //text를 그려줌

        }
    }
}
