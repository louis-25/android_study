package com.example.training12_2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtName, edtNumber, edtNameResult, edtNumberResult;
    Button btnInit, btnInsert, btnSelect, btnUpdate, btnDelete;
    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("가수 그룹 관리 DB(수정)");
        edtName = (EditText) findViewById(R.id.edtName);
        edtNumber = (EditText) findViewById(R.id.edtNumber);
        edtNameResult = (EditText) findViewById(R.id.edtNameResult);
        edtNumberResult = (EditText) findViewById(R.id.edtNumberResult);
        btnInit = (Button) findViewById(R.id.btnInit);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        myHelper = new myDBHelper(this);
        // 시작할때 먼저 조회
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);
        String strNames = "그룹이름" + "\r\n" + "--------" + "\r\n";
        String strNumbers = "인원" + "\r\n" + "--------" + "\r\n";
        while (cursor.moveToNext()) {
            strNames += cursor.getString(0) + "\r\n";
            strNumbers += cursor.getString(1) + "\r\n";
        }
        edtNameResult.setText(strNames);
        edtNumberResult.setText(strNumbers);
        cursor.close();
        sqlDB.close();
        btnInit.setOnClickListener(new View.OnClickListener() { //초기화
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
                // 초기화 결과
                Toast.makeText(getApplicationContext(), "초기화됨",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() { //조회
            public void onClick(View v) {
                // 키보드 닫기
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(edtName.getWindowToken(), 0);
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);
                String strNames = "그룹이름" + "\r\n" + "--------" + "\r\n";
                String strNumbers = "인원" + "\r\n" + "--------" + "\r\n";
                while (cursor.moveToNext()) {
                    strNames += cursor.getString(0) + "\r\n";
                    strNumbers += cursor.getString(1) + "\r\n";
                }
                edtNameResult.setText(strNames);
                edtNumberResult.setText(strNumbers);
                cursor.close();
                sqlDB.close();
                // 처리결과 확인
                Toast.makeText(getApplicationContext(), "조회됨",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() { //입력
            public void onClick(View v) {
                // 키보드 닫기
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(edtNumber.getWindowToken(), 0);
                try {
                    //DB INSERT
                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO groupTBL VALUES ( '"
                            + edtName.getText().toString().trim() + "' , "
                            + edtNumber.getText().toString().trim() + ");");
                    sqlDB.close();
                    // 입력결과 확인
                    btnSelect.callOnClick(); //조회
                } catch (Exception e) {
                    // SQL오류 출력
                    Toast.makeText(getApplicationContext(),
                            "SQL오류 :" + e.toString(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() { // 수정
            public void onClick(View v) {
                // 키보드 닫기
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(edtNumber.getWindowToken(), 0);
                try {
                    //DB UPDATE
                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL("UPDATE groupTBL set gNumber = "
                            + edtNumber.getText().toString().trim()
                            + " where gName = '"
                            + edtName.getText().toString().trim() + "';");
                    sqlDB.close();
                    // 처리결과 확인
                    btnSelect.callOnClick(); //조회

                } catch (Exception e) {
                    // SQL오류 출력
                    Toast.makeText(getApplicationContext(),
                            "SQL오류 :" + e.toString(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() { //삭제
            public void onClick(View v) {
                // 키보드 닫기
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(edtNumber.getWindowToken(), 0);
                try {
                    //DB DELETE
                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL("DELETE from groupTBL where gName = '"
                            + edtName.getText().toString().trim() + "';");
                    sqlDB.close();
                    // 처리결과 확인
                    btnSelect.callOnClick(); //조회

                } catch (Exception e) {
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
            super(context, "groupDB", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE  groupTBL (gName CHAR(20) PRIMARY KEY, gNumber INTEGER);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);
        }
    }
}