package com.example.hello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginOkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);

        CheckBox checkBox1 = (CheckBox)findViewById(R.id.checkBox);
        CheckBox checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
        CheckBox checkBox3 = (CheckBox)findViewById(R.id.checkBox3);

        Button qr_button = (Button)findViewById(R.id.button4);
        Button logout = (Button)findViewById(R.id.button9);
        Button call_button = (Button)findViewById(R.id.button5);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBox1.isChecked()||!checkBox2.isChecked()||!checkBox3.isChecked()){
                    Toast.makeText(getApplicationContext(),"체크리스트를 확인하세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    //QR 코드 찍을 수 있도록 페이지 넘김
                    Intent intent = new Intent(LoginOkActivity.this, ScannerActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-5584-2372"));
                startActivity(cIntent);
            }
        });

    }


}
