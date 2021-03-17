package com.example.test_2048game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import java.util.Random;   // 난수생성 라이브러리

public class MainActivity extends AppCompatActivity {
    public static int[][] arr = new int[4][4];

    public void New_num(int[][] arr){
        int i, j, d, rand, row, column, count=0;
        Random random = new Random();
        for (i = 0; i < 4; i++) {  //초기 게임판
            for (j = 0; j < 4; j++) {
                if (arr[i][j] == 0)	count++;
                if (count == 16) {	//게임판이 모두 0일 경우(처음)
                    for (d = 0; d < 2; d++) { //랜덤하게 2군데에 카드 2 생성
                        rand = random.nextInt(16) % 17; // 1~16
                        row = rand / 4; //행
                        column = rand % 4; //열
                        if(arr[row][column]==0)
                            arr[row][column] = 2;
                        else
                            d--;
                    }
                }

            }
        }
        count = 0;
        while(true) {
            for(i=0; i<4 ; i++) {
                for(j=0; j<4; j++) {
                    if(arr[i][j] != 0) count++;
                }
            }
            if(count == 16) break; //게임판에 카드가 전부 차있는경우

            rand = random.nextInt(16) % 17;
            row = rand / 4;
            column = rand % 4;
            if(arr[row][column] != 0) { //카드를 생성할 칸에 숫자가 있는지?
                continue;
            }
            if(rand % 2 == 0)   {//랜덤한 난수를 생성하여 짝수면 2 홀수면 4를 생성
                arr[row][column] = 2;
                break;
            }
            else	{
                arr[row][column] = 4;
                break;
            }
        }
    }

    public boolean Game_over() {
        int i, j, game_over = 0;
        boolean check = false;
        for (i = 0; i < 4; i++) {	//왼쪽
            for (j = 1; j < 4; j++) {
                if (arr[i][j - 1] == arr[i][j]) {
                    game_over = 0;
                }
                else if (arr[i][j - 1] == 0) {
                    game_over = 0;
                }
                else if (arr[i][j - 1] != arr[i][j])	game_over = 1;
            }
        }

        for (i = 0; i < 4; i++) {	//오른쪽
            for (j = 2; j >= 0; j--) {
                if (arr[i][j + 1] == arr[i][j]) {
                    game_over = 0;
                }
                else if (arr[i][j + 1] == 0) {
                    game_over = 0;
                }
                else if (game_over == 1 && arr[i][j + 1] != arr[i][j])	game_over = 2;
            }
        }
        for (j = 0; j < 4; j++) {	//위
            for (i = 1; i < 4; i++) {
                if (arr[i - 1][j] == arr[i][j]) {
                    game_over = 0;
                }
                else if (arr[i - 1][j] == 0) {
                    game_over = 0;
                }
                else if (game_over == 2 && arr[i - 1][j] != arr[i][j])	game_over = 3;
            }
        }
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 3; i++) {
                if (arr[i + 1][j] == arr[i][j]) {
                    game_over = 0;
                }
                else if (arr[i + 1][j] == 0) {
                    game_over = 0;
                }
                else if (game_over == 3 && arr[i + 1][j] != arr[i][j])	check = true;
            }
        }

        return check;
    }

    public void Draw(int[][] arr){
        Button btn0 = (Button) findViewById(R.id.arr_button0);
        Button btn1 = (Button) findViewById(R.id.arr_button1);
        Button btn2 = (Button) findViewById(R.id.arr_button2);
        Button btn3 = (Button) findViewById(R.id.arr_button3);
        Button btn4 = (Button) findViewById(R.id.arr_button4);
        Button btn5 = (Button) findViewById(R.id.arr_button5);
        Button btn6 = (Button) findViewById(R.id.arr_button6);
        Button btn7 = (Button) findViewById(R.id.arr_button7);
        Button btn8 = (Button) findViewById(R.id.arr_button8);
        Button btn9 = (Button) findViewById(R.id.arr_button9);
        Button btn10 = (Button) findViewById(R.id.arr_button10);
        Button btn11 = (Button) findViewById(R.id.arr_button11);
        Button btn12 = (Button) findViewById(R.id.arr_button12);
        Button btn13 = (Button) findViewById(R.id.arr_button13);
        Button btn14 = (Button) findViewById(R.id.arr_button14);
        Button btn15 = (Button) findViewById(R.id.arr_button15);

        btn0.setText(String.valueOf(arr[0][0]));
        btn1.setText(String.valueOf(arr[0][1]));
        btn2.setText(String.valueOf(arr[0][2]));
        btn3.setText(String.valueOf(arr[0][3]));
        btn4.setText(String.valueOf(arr[1][0]));
        btn5.setText(String.valueOf(arr[1][1]));
        btn6.setText(String.valueOf(arr[1][2]));
        btn7.setText(String.valueOf(arr[1][3]));
        btn8.setText(String.valueOf(arr[2][0]));
        btn9.setText(String.valueOf(arr[2][1]));
        btn10.setText(String.valueOf(arr[2][2]));
        btn11.setText(String.valueOf(arr[2][3]));
        btn12.setText(String.valueOf(arr[3][0]));
        btn13.setText(String.valueOf(arr[3][1]));
        btn14.setText(String.valueOf(arr[3][2]));
        btn15.setText(String.valueOf(arr[3][3]));
    }

    @SuppressLint("ClickableViewAccessibility")
    public void Movement(){
        ConstraintLayout test = findViewById(R.id.Layout_basic); //ConstraintLayout 객체생성
        test.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) { //스와이프 객체생성
            int i, j, c, count = 0;
            boolean move_check = false;
            public void onSwipeTop() {
                move_check = false;
                for(j=0; j<4; j++) {
                    count = 0;
                    for(i=0; i<3; i++) {
                        if(Game_over() == true) { //게임 종료조건 확인
                            Draw(arr);
                            Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if((arr[i][j] == 0) && (arr[i+1][j] != 0)) { //키 입력방향으로 push하기
                            arr[i][j] = arr[i+1][j];
                            arr[i+1][j] = 0;
                            i=-1; //한번 당겼으면 다시 처음부터 검사
                            move_check = true;
                            continue;
                        }
                    }
                    for(i=0; i<3; i++) {
                        if((arr[i][j] == arr[i+1][j]) && (arr[i][j] != 0)) { //같은 숫자면 더해주기(0더하기 제외)
                            arr[i][j] += arr[i+1][j];
                            arr[i+1][j] = 0;
                            move_check = true;
                        }
                        if((arr[i][j] == 0) && (arr[i+1][j] != 0)) { //키 입력방향으로 push하기
                            arr[i][j] = arr[i+1][j];
                            arr[i+1][j] = 0;
                            move_check = true;
                        }
                    }
                    System.out.println(move_check);
                }
                if(move_check == true) New_num(arr); //숫자 이동 후 새로운 카드생성
                Draw(arr);

            }
            public void onSwipeRight() {
                move_check = false;
                for(i=0; i<4; i++) {
                    count = 3;
                    for(j=3; j>0; j--) {
                        if(Game_over() == true) {
                            Draw(arr);
                            Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if((arr[i][j] == 0) && (arr[i][j-1] != 0)) {
                            arr[i][j] = arr[i][j-1];
                            arr[i][j-1] = 0;
                            j=4; //다시 처음부터
                            move_check = true;
                            continue;
                        }
                    }
                    for(j=3; j>0; j--) {
                        if((arr[i][j] == arr[i][j-1]) && (arr[i][j] != 0)) {
                            arr[i][j] += arr[i][j-1];
                            arr[i][j-1] = 0;
                            move_check = true;
                        }
                        if((arr[i][j] == 0) && (arr[i][j-1] != 0)) {
                            arr[i][j] = arr[i][j-1];
                            arr[i][j-1] = 0;
                            move_check = true;
                        }
                    }

                    System.out.println(move_check);
                }

                if(move_check == true) New_num(arr);
                Draw(arr);
            }
            public void onSwipeLeft() {
                move_check = false;
                for(i=0; i<4; i++) {
                    count = 0;
                    for(j=0; j<3; j++) {
                        if(Game_over() == true) {
                            Draw(arr);
                            Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if((arr[i][j] == 0) && (arr[i][j+1] != 0)) { //숫자가 이동하는 경우
                            arr[i][j] = arr[i][j+1];
                            arr[i][j+1] = 0;
                            j=-1; //다시 처음부터
                            move_check = true;
                            continue;
                        }
                    }
                    for(j=0; j<3; j++) {
                        if((arr[i][j] == arr[i][j+1]) && (arr[i][j] != 0)) { //숫자가 더해지는 경우(0끼리 더하는 경우 제외)
                            arr[i][j] += arr[i][j+1];
                            arr[i][j+1] = 0;
                            move_check = true;

                        }
                        if((arr[i][j] == 0) && (arr[i][j+1] != 0)) { //숫자가 이동하는 경우
                            arr[i][j] = arr[i][j+1];
                            arr[i][j+1] = 0;
                            move_check = true;
                        }
                    }
                    //System.out.println(move_check);
                }

                if(move_check == true) New_num(arr);
                Draw(arr);
            }
            public void onSwipeBottom() {
                move_check = false;
                for(j=0 ; j<4 ; j++) {
                    count = 3;
                    for(i=3; i>0; i--) {
                        if(Game_over() == true) {
                            Draw(arr);
                            Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if((arr[i][j] == 0) && (arr[i-1][j] != 0)) {
                            arr[i][j] = arr[i-1][j];
                            arr[i-1][j] = 0;
                            i=4; //다시 처음부터
                            move_check = true;
                            continue;
                        }
                    }
                    for(i=3; i>0; i--) {
                        if((arr[i][j] == arr[i-1][j]) && (arr[i][j] != 0)) {
                            arr[i][j] += arr[i-1][j];
                            arr[i-1][j] = 0;
                            move_check = true;
                        }
                        if((arr[i][j] == 0) && (arr[i-1][j] != 0)) {
                            arr[i][j] = arr[i-1][j];
                            arr[i-1][j] = 0;
                            move_check = true;
                        }
                    }
                    //System.out.println(move_check);
                }

                if(move_check == true) New_num(arr);
                Draw(arr);

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {      //처음 실행시키는 부분
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout test = findViewById(R.id.Layout_basic); //ConstraintLayout 객체생성
        int count = 0;
        New_num(arr);
        Draw(arr);
        Movement();

    }

    //Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
}
