package com.example.hello;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_page);

        Button back_button = (Button) findViewById(R.id.button8);
        Button join_button = (Button) findViewById(R.id.button7);

        EditText idText = (EditText)findViewById(R.id.editTextTextPersonName2);
        EditText pwText = (EditText)findViewById(R.id.editTextTextPassword2);
        EditText morepwText = (EditText)findViewById(R.id.editTextTextPassword3);
        EditText nameText = (EditText)findViewById(R.id.editTextTextPersonName3);
        EditText birthText = (EditText)findViewById(R.id.editTextDate);
        EditText phoneText = (EditText)findViewById(R.id.editTextPhone);

        idText.setFocusableInTouchMode(true);
        pwText.setFocusableInTouchMode(true);
        morepwText.setFocusableInTouchMode(true);
        nameText.setFocusableInTouchMode(true);
        birthText.setFocusableInTouchMode(true);
        phoneText.setFocusableInTouchMode(true);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idText.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"아이디를 입력하세요.",Toast.LENGTH_SHORT).show();
                    idText.requestFocus();
                }
                else if(pwText.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요.",Toast.LENGTH_SHORT).show();
                    pwText.requestFocus();
                }
                else if(morepwText.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"비밀번호 확인을 입력하세요.",Toast.LENGTH_SHORT).show();
                    morepwText.requestFocus();
                }
                else if(nameText.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"이름을 입력하세요.",Toast.LENGTH_SHORT).show();
                    nameText.requestFocus();
                }
                else if(birthText.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"생년월일을 입력하세요.",Toast.LENGTH_SHORT).show();
                    birthText.requestFocus();
                }
                else if(phoneText.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"전화번호을 입력하세요.",Toast.LENGTH_SHORT).show();
                    phoneText.requestFocus();
                }
                else if(!pwText.getText().toString().equalsIgnoreCase(morepwText.getText().toString())){
                    Toast.makeText(getApplicationContext(),"비밀번호를 다시 확인하세요.",Toast.LENGTH_SHORT).show();
                    morepwText.requestFocus();
                }
                else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(JoinActivity.this);
                    builder.setTitle("");
                    builder.setMessage(idText.getText().toString()+"님 환영합니다 !!!");
                    builder.setPositiveButton("확인",null);
                    builder.create().show();
                }
            }
        });
    }
}
