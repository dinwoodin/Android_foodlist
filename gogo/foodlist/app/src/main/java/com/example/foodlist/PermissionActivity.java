package com.example.foodlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;

public class PermissionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        checkAndRequestPermission();

        Intent intent=new Intent(this,IndexActivity.class);
        startActivity(intent);
        finish();

    }
    //권한 설정 (지도 카메라 이미지 파일 권한 설정, 설치시 처음에 물어보게끔함)
    private void checkAndRequestPermission(){
        String[] permissions={Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
        ArrayList<String> permissionsNeeded =new ArrayList<>();
        for(String permission:permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionsNeeded.add(permission);
            }
        }
        if(!permissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]),100);

        }
    }

}