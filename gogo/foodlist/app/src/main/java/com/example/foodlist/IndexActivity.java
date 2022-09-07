package com.example.foodlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    //시작되고 나서 메인화면으로 이동
    @Override
    protected void onStart() {
        super.onStart();
        Handler mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(IndexActivity.this,MainActivity.class);
                startActivity(intent);
                //finish를 하지 않으면 반복실행 후 오류가남
                finish();

            }
        }, 300); //1000=1초, 1초동안 보여주고 보인화면으로 넘어감
    }
}