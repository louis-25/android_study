package com.example.project_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    final static int LINE = 1, CIRCLE = 2, RECTANGLE = 3;
    static int curShape = LINE, curColor = Color.BLACK; //현재의 (도형 / 색깔)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));
        setTitle("간단 그림판 (개선)");
    }

    private static class MyGraphicView extends View {
        int startX = -1, startY = -1, stopX = -1, stopY = -1;
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) { //터치이벤트
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    this.invalidate(); //onDraw호출
                    break; //위 2개의 case를 묶은 이유는 마우스에 따라가는 모습을 보이기 위해서
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true); //끝처리 부드럽게
            paint.setStrokeWidth(5); //굵기 세팅
            paint.setStyle(Paint.Style.STROKE); //도형의 선을 STROKE로 선택
            paint.setColor(curColor); //현재의 컬러를 세팅

            switch(curShape) { //현재의 도형
                case LINE:
                    canvas.drawLine(startX,startY,stopX,stopY,paint); //선그리기
                    break;
                case CIRCLE:
                    int radius = (int) Math.sqrt(Math.pow(startX-startX, 2)
                    +Math.pow(stopY - startY,2));
                    canvas.drawCircle(startX,startY,radius,paint); //원그리기
                    break;
                case RECTANGLE:
                    Rect rect = new Rect(startX,startY,stopX,stopY);
                    canvas.drawRect(rect,paint); //사각형 그리기
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //메뉴 생성
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"선 그리기");
        menu.add(0,2,0,"원 그리기");
        menu.add(0,3,0,"사각형 그리기");
        SubMenu sMenu = menu.addSubMenu("색상변경 >>");
        sMenu.add(0,4,0,"빨강");
        sMenu.add(0,5,0,"초록");
        sMenu.add(0,6,0,"파랑");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //메뉴가 선택되었을때
        switch (item.getItemId()) {
            case 1:
                curShape = LINE;
                return true;
            case 2:
                curShape = CIRCLE;
                return true;
            case 3:
                curShape = RECTANGLE;
                return true;
            case 4:
                curColor = Color.RED;
                return true;
            case 5:
                curColor = Color.GREEN;
                return true;
            case 6:
                curColor = Color.BLUE;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

