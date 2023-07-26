package com.example.hello;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.time.LocalDateTime;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
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
//                 JSONObject obj = new JSONObject(result.getContents()) 사용하면 obj.getString()으로 안에 스트링 값 따로 추출 가능
//                Toast.makeText(this,"스캔: " + result.getContents(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"스캔: " + getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", getIntent().getStringExtra("id"));
                contentValues.put("place", result.getContents());

                ScannerTask scannerTask = new ScannerTask("http://75ef3b773f93.ngrok.io/places", contentValues);
                scannerTask.execute();

                Intent intent = new Intent(ScannerActivity.this,LoginOkActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class ScannerTask extends AsyncTask<Void, Void, String>{
        String url;
        ContentValues values;

        public ScannerTask(String url, ContentValues values) {
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