package com.example.firebase_realtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private Button sendbt;
    private EditText editdt;
    public String msg;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseDatabase mDatabase; //데이터베이스 접근점
    private DatabaseReference mReference; //데이터베이스 주소
    private ChildEventListener mChild;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editdt = (EditText) findViewById(R.id.editText);
        sendbt = (Button) findViewById(R.id.button_test);
        listView = (ListView) findViewById(R.id.Listviemsg);

        initDatabase();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        listView.setAdapter(adapter);


        sendbt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // 버튼 누르면 수행 할 명령
                msg = editdt.getText().toString(); //editext에서 값을 읽어들임
                editdt.setText("");
                databaseReference.child("message").push().setValue(msg);
                Toast.makeText(MainActivity.this,"test", Toast.LENGTH_SHORT).show();
            }
        });
        mReference = mDatabase.getReference("message"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear(); //listview 초기화
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {   // child 내에 있는 데이터만큼 반복합니다.

                    // child 내에 있는 데이터 저장하는 방법
                    String msg2 = messageData.getValue().toString();
                    //Toast.makeText(MainActivity.this,msg2, Toast.LENGTH_SHORT).show();
                    //Array.add(msg2);
                    //adapter.add(msg2);
                    adapter.insert(msg2,0);
                    //listView.smoothScrollToPosition(0); // 리스트뷰의 최상단으로 스크롤
                }
                adapter.notifyDataSetChanged(); //리스트뷰 갱신
                listView.setSelection(0); //마지막위치를 카운트하여 보내주기 (adapter.getCount() - 1)
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("log");
        mReference.child("log").setValue("check");

        mChild = new ChildEventListener() {

            @Override //항목 목록을 검색하거나 추가에 대한 수신대기
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override //목록의 항목에 대한 변경을 수신대기
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override//목록의 항목삭제를 수신대기
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override//순서있는 목록의 항목 순서변경을 수신대기
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }
}
