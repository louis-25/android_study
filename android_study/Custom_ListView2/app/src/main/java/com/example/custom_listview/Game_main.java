package com.example.custom_listview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class Game_main extends AppCompatActivity {

    //실시간 데이터베이스
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    private String Id;
    private String Name;
    private String Email;
    private Uri personPhoto;
    private String msg;
    private ListView mListView;

    private Button sendButton;
    private EditText editText;
    //boolean check = false;

    //public int count=0;
    //커스텀뷰 어댑터
    MyAdapter mMyAdapter = new MyAdapter();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        sendButton = (Button) findViewById(R.id.sendButton);
        editText = (EditText) findViewById(R.id.editText);

        /* 위젯과 멤버변수 참조 획득 */
        mListView = (ListView)findViewById(R.id.listView);
        //전송버튼 클릭 시
        sendButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = editText.getText().toString();
                if(msg.trim().length() == 0){
                    Toast.makeText(Game_main.this,"내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    editText.setText("");
                    String Uri_string = personPhoto.toString();
                    ChatData chatData = new ChatData(msg, Name, Id, Uri_string);
                    databaseReference = firebaseDatabase.getReference();
                    databaseReference.child("message").push().setValue(chatData);
                    Toast.makeText(Game_main.this, "전송", Toast.LENGTH_SHORT).show();
                }

            }
        });

        databaseReference = firebaseDatabase.getReference("message"); // 변경값을 확인할 child 이름
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                Log.v("Msg",chatData.getMessage());
                Log.v("ID",chatData.getUser_Id());
                Log.v("Name",chatData.getUser_Name());
                Log.v("Uri",chatData.getUser_Uri());
                Uri User_Uri = Uri.parse(chatData.getUser_Uri());
                mMyAdapter.addItem(User_Uri,chatData.getUser_Name(),chatData.getMessage());
                mListView.setAdapter(mMyAdapter);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        //구글 로그인정보 가져오기
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null) {
            Email = acct.getEmail();
            Id = acct.getId();
            Name = acct.getDisplayName();
            personPhoto = acct.getPhotoUrl();
        }
        //데이터베이스에 사용자 정보 입력하기
        String Uri_string = personPhoto.toString();
        databaseReference = firebaseDatabase.getReference("사용자");
        databaseReference.child(Id).child("Name").setValue(Name);
        databaseReference.child(Id).child("Email").setValue(Email);
        databaseReference.child(Id).child("Uri").setValue(Uri_string);

    }//OnCreate

}
