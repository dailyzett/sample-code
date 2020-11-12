package com.example.hello;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText idText = (EditText)findViewById(R.id.editTextTextPersonName);
        final EditText pwText = (EditText)findViewById(R.id.editTextTextPassword);

        Button joinbutton = (Button) findViewById(R.id.button2);
        joinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
        Button loginbutton = (Button) findViewById(R.id.button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idText.getText().toString();
                String pw = pwText.getText().toString();


                ContentValues values = new ContentValues();
                values.put("id", id);
                values.put("pw", pw);

                Task task = new Task("http://f7f02859cffb.ngrok.io/vision", values);
                try {
                    String s = task.execute().get();
                    JSONObject jsonObject = new JSONObject(s);

                    String trueid = jsonObject.getString("username");
                    String truepw = jsonObject.getString("password");

                    if(id.equalsIgnoreCase(trueid)&&pw.equalsIgnoreCase(truepw)){
                        Intent intent = new Intent(LoginActivity.this,LoginOkActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"아이디 혹은 비밀번호가 맞지 않습니다.",Toast.LENGTH_LONG).show();
                        idText.setText(null);
                        pwText.setText(null);
                        //return;
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                String trueid = "root";
//                String truepw = "1234";
//
//                if(id.equalsIgnoreCase(trueid)&&pw.equalsIgnoreCase(truepw)){
//                    Intent intent = new Intent(LoginActivity.this,LoginOkActivity.class);
//                    startActivity(intent);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"아이디 혹은 비밀번호가 맞지 않습니다.",Toast.LENGTH_LONG).show();
//                    idText.setText(null);
//                    pwText.setText(null);
//                    //return;
//                }
            }
        });
    }
    public class Task extends AsyncTask<Void, Void, String>{
        String url;
        ContentValues values;

        public Task(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.

        }
    }
}