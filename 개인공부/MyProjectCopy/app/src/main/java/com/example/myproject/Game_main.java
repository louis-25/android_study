package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.BreakOut_Game.BreakOutEngine;
import com.example.myproject.Custom_ListView.MyAdapter;
import com.example.myproject.MyQRCode.CreateQR;
import com.example.myproject.MyQRCode.ScanQR;
import com.example.myproject.BreakOut_Game.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Game_main extends AppCompatActivity {

    private Button createQRBtn;
    private Button scanQRBtn;
    private Button GameBtn;
    private Button LogoutBtn;

    private Button sendButton;
    private EditText editText;
    public String msg;

    private String Id;
    private String Name;
    private String Email;
    private Uri personPhoto;
    ImageView imageView;
    TextView name, email, id;

    //리스트뷰
    private ListView mListView;
    private ListView mListView_info;
    //커스텀뷰 어댑터
    MyAdapter mMyAdapter = new MyAdapter();
    MyAdapter mMyAdapter2 = new MyAdapter();
    //실시간 데이터베이스
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        //QR코드, 로그아웃
        createQRBtn = (Button) findViewById(R.id.createQR);
        GameBtn = (Button) findViewById(R.id.game_start);
        LogoutBtn = (Button) findViewById(R.id.LogoutBtn);
        //메세지 입력, 전송, 출력
        editText = (EditText) findViewById(R.id.Insert_text);
        sendButton = (Button) findViewById(R.id.send_btn);
        mListView = (ListView) findViewById(R.id.Listviewmsg);
        //이미지, 이름, 이메일, 아이디
        /*imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        id = findViewById(R.id.textID);*/
        //꽉찬화면
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ");
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.");
        }
// 몰입 모드를 꼭 적용해야 한다면 아래의 3가지 속성을 모두 적용시켜야 합니다
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //꽉찬화면

        //전송버튼
        sendButton.setOnClickListener(new Button.OnClickListener() {
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


        //실시간 데이터베이스
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

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null) {  //구글로그인 정보 가져오기
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

        //사용자 정보 표시
        mListView_info = (ListView) findViewById(R.id.Listview_info);
        mMyAdapter2.addItem(personPhoto,"Email : "+Email,"Name : "+Name);
        mListView_info.setAdapter(mMyAdapter2);



        //QR코드 버튼
        createQRBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game_main.this, CreateQR.class);
                startActivity(intent);
            }
        });
       /* //QR스캔 버튼
        scanQRBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game_main.this, ScanQR.class);
                startActivity(intent);
            }
        });*/
        //로그아웃 버튼
        LogoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                //signOut();
                //revokeAccess();

                Intent intent = new Intent(Game_main.this, com.example.myproject.MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"로그아웃",Toast.LENGTH_SHORT).show();//토스트 메세지
            }
        });
        //게임버튼
        GameBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game_main.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() { //뒤로가기버튼 막아두기
        //super.onBackPressed();
    }
}
