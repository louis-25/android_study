package com.example.example6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker dp;
    EditText edtDiary;
    Button btnWrite;
    String Diary_Date;
    String Diary_Content;

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 일기장(수정)");

        dp = findViewById(R.id.datePicker1);
        edtDiary = findViewById(R.id.edtDiary);
        btnWrite = findViewById(R.id.btnWrite);

        Calendar cal = Calendar.getInstance();
        final int cYear = cal.get(Calendar.YEAR);
        final int cMonth = cal.get(Calendar.MONTH)+1;
        final int cDay = cal.get(Calendar.DAY_OF_MONTH);

        Diary_Date = cYear+"_"+cMonth+"_"+cDay;
        myHelper = new myDBHelper(this);
        try {
            //시작할때 일기보여주기
            sqlDB = myHelper.getReadableDatabase();
            Cursor cursor;
            cursor = sqlDB.rawQuery("SELECT * FROM myDiary WHERE Diarydate ='"+Diary_Date+"';", null);
            cursor.moveToNext();
            String str = cursor.getString(1)+"\n";
            edtDiary.setText(str);

            cursor.close();
            sqlDB.close();
        }catch (Exception e){

        }

        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                    edtDiary.setText("");
                    Diary_Date = Integer.toString(year) +"_"
                            +Integer.toString(monthOfYear)+"_"
                            +Integer.toString(dayOfMonth);
                    sqlDB = myHelper.getReadableDatabase();
                    Cursor cursor;
                    cursor = sqlDB.rawQuery("SELECT * FROM myDiary WHERE Diarydate ='"+Diary_Date+"';",null);
                    //날짜 - 내용
                    cursor.moveToNext();
                    String str = cursor.getString(1)+"\n";
                    edtDiary.setText(str); // 일기내용 세팅
                    btnWrite.setEnabled(true);
                    btnWrite.setText("수정하기");

                    Toast.makeText(getApplicationContext(),Diary_Date+"으로 날짜변경",Toast.LENGTH_SHORT).show();

                    cursor.close();
                    sqlDB.close();
                }catch (Exception e){
                    edtDiary.setText("");
                    edtDiary.setHint("일기없음");
                    btnWrite.setText("새로저장");
                }
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //DB INSERT
                    Diary_Content = edtDiary.getText().toString();
                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO myDiary VALUES ('"+Diary_Date+"','"+Diary_Content+"');");
                    Toast.makeText(getApplicationContext(),Diary_Date+"일기 저장됨",Toast.LENGTH_SHORT).show();
                    sqlDB.close();
                }catch (Exception e){
                    // SQL오류 출력
                    Toast.makeText(getApplicationContext(),
                            "SQL오류 :" + e.toString(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "myDB", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE  myDiary (Diarydate CHAR(20) PRIMARY KEY, Content VARCHAR(500));");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS myDiary");
            onCreate(db);
        }
    }
}
