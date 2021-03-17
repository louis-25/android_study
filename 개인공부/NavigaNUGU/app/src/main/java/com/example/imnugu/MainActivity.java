package com.example.imnugu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private SignInButton btn_google;   //구글 로그인 버튼
    private FirebaseAuth auth;   //파이어 베이스 인증 객체
    private GoogleApiClient googleApiClient;  //구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //앱이 실행될때 수행되는 곳
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance(); // 파이어베이스 인증 객체 초기화

        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() { // 구글 로그인 버튼을 클릭했을때 이곳을 수행
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //구글 로그인 인증을 요청했을떄 결과값을 돌려받음
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) { //인증 결과가 성공적이면?
                GoogleSignInAccount account = result.getSignInAccount(); //account라는 데이터는 구글 로그인 정보를 담고있음(닉네임, 프로필사진, 이메일 주소)
                resultLogin(account); //로그인 결과 값을 출력하라는 메서드

            }
        }
    }

    String getID =new String();
    String name =new String();
    String emailauth = new String();

    private void resultLogin(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {  //로그인이 성공 했으면,

                            getID = account.getId();
                            name = account.getGivenName();
                            emailauth = account.getEmail();

                            Log.d(TAG, "data = "+getID);
                            Log.d(TAG, "data = "+name);
                            Log.d(TAG, "data = "+emailauth);

                            //
                            JsonObject json = new JsonObject();
                            json.addProperty("token", getID);
                            json.addProperty("name", name);
                            json.addProperty("email", emailauth);

                            final OkHttpClient client = new OkHttpClient();
                            final Request request = new Request.Builder()
                                    .url("http://35.232.181.79:3000/oauth/useToken")
                                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                                            json.toString()))
                                    .build();

                            AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
                                @Override
                                protected String doInBackground(Void... params) {
                                    //List<statusResult> tkList = new ArrayList<>();
                                    try {
                                        Response response = client.newCall(request).execute();
                                        System.out.println(response);
                                        if (!response.isSuccessful()) {
                                            return null;
                                        }

                                        return response.body().string();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return null;
                                    }

                                }

                                @Override
                                protected void onPostExecute(String s) {
                                    super.onPostExecute(s);
                                    if (s != null) {
                                        System.out.println(s);

                                        try {
                                            JSONObject jsonObject = new JSONObject(s);
                                            String status = jsonObject.getString("status");
                                            String result = jsonObject.getString("result");
                                            if(status.equals("200")){
                                                Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                                                intent.putExtra("getID", account.getId());
                                                intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl())); //String.valueOf 특정 자료형을 String형태로 변환
                                                intent.putExtra("email", account.getEmail());
                                                intent.putExtra("name", account.getDisplayName());
                                                startActivity(intent);

                                                Log.d(TAG, "data = "+getID);
                                                Log.d(TAG, "data = "+name);
                                                Log.d(TAG, "data = "+emailauth);
                                                finish();
                                            }

                                            else{
                                                Toast.makeText(MainActivity.this, "서버 인증 실패", Toast.LENGTH_SHORT).show();
                                            }
                                            //System.out.println(Status);
                                            //System.out.println(result);
                                        }

                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            };
                            asyncTask.execute();


                            /*
                            Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("nickName", account.getDisplayName());
                            intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl())); //String.valueOf 특정 자료형을 String형태로 변환
                            intent.putExtra("email", account.getEmail());
                            startActivity(intent);

                            Log.d(TAG, "data = "+nicknameauth);
                            Log.d(TAG, "data = "+emailauth);

                            ///////이제 nicknameauth, emailauth로 광희형이 준 파일 합치면 완성


                             */

                        }
                        else {
                            Toast.makeText(MainActivity.this, "구글 로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}