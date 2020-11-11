package com.example.hello;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScannerActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.setPrompt("스캔 중");
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if (result.getContents() == null) {
                Toast.makeText(this, "취소",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ScannerActivity.this,LoginOkActivity.class);
                startActivity(intent);
            } else {
                // get.Content()를 하면 값을 얻어옴
                // JSONObject obj = new JSONObject(result.getContents()) 사용하면 obj.getString()으로 안에 스트링 값 따로 추출 가능
                Toast.makeText(this,"스캔: " + result.getContents(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ScannerActivity.this,LoginOkActivity.class);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}