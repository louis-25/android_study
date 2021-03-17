package com.example.google_login2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Test_Activity extends AppCompatActivity  {
    private FirebaseAuth mAuth;

    ImageView imageView;
    TextView name, email, id;
    Button signOut;

    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInApi mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        id = findViewById(R.id.textID);
        signOut = (Button) findViewById(R.id.Loout_button);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.Loout_button: //로그아웃 버튼
                        FirebaseAuth.getInstance().signOut();
                        //revokeAccess();
                        //mAuth.signOut();
                        //signOut();
                        Toast.makeText(getApplicationContext(),"로그아웃.",Toast.LENGTH_SHORT).show();//토스트 메세지
                        finish();
                        break;
                }
            }
        });



        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null) {  //구글로그인 정보 가져오기
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            email.setText(personEmail);
            id.setText(personId);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView); //Glide 라이브러리 사용
        }
    }


    /*private void signOut() { // 로그아웃
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Intent intent = new Intent(Test_Activity.this, MainActivity.class);
                        Toast.makeText(getApplicationContext(),"로그아웃.",Toast.LENGTH_SHORT).show();//토스트 메세지
                        startActivity(intent);
                    }
                });
    }*/




    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
            @Override
    public void onBackPressed() { //뒤로가기버튼 막아두기
        //super.onBackPressed();
    }

    private void revokeAccess() { // 계정 연결해제
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(Test_Activity.this, "계정 연결해제", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                });
    }





}
