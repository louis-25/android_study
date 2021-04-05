package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView idText = (TextView) findViewById(R.id.idText);
        TextView passwordText = (TextView) findViewById(R.id.passwordText);
        TextView welcomMessage = (TextView) findViewById(R.id.welcomMessage);
        Button managementButton = (Button) findViewById(R.id.managementButton);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");
        String message = "환영합니다 " + userID + "님!";

        idText.setText(userID);
        passwordText.setText(userPassword);
        welcomMessage.setText(message);

        if(!userID.equals("admin"))
        {
            managementButton.setVisibility(View.GONE); //admin계정이 아니면 버튼이 안보임
        }

        managementButton.setOnClickListener(new View.OnClickListener(){ //회원관리 버튼
            @Override
            public void onClick(View view){
                new BackgroundTask().execute();
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){ // 아래의 php파일로 초기화하는 부분
            target = "http://louis96.dothome.co.kr/List.php";
        }

        @Override
        protected String doInBackground(Void... voids){ // 실질적으로 실행이 되는부분
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();  //웹 파싱부분
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim(); //해당 문자열의 집합 반환
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this,ManagementActivity.class);
            intent.putExtra("userList",result);
            startActivity(intent);
        }
    }
}
