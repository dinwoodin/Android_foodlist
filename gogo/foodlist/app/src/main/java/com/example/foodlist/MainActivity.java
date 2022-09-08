package com.example.foodlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    RelativeLayout main_drawer;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    FoodVO vo=new FoodVO();
    ImageView keep;

    //메인 content에 띄울 화면 생성
    Fragment foodListFragment=new FoodListFragment();
    Fragment foodMapFragment=new FoodMapFragment();
    Fragment foodKeepFragment=new FoodKeepFragment();
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawerLayout);
        main_drawer=findViewById(R.id.main_drawer);


        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content,foodListFragment).commit();
        //데이터베이스 생성
//        helper=new FoodDB(this);
//        db=helper.getReadableDatabase();

        keep=findViewById(R.id.keep);

        getSupportActionBar().setTitle("맛집리스트");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_mama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView=findViewById(R.id.drawer_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.nav_profile:
                        Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_list:
                        transaction.replace(R.id.main_content,foodListFragment).commit();
                        getSupportActionBar().setTitle("맛집리스트");
                        drawerLayout.closeDrawer(main_drawer);
                        break;
                    case R.id.nav_map:
                        transaction.replace(R.id.main_content,foodMapFragment).commit();
                        getSupportActionBar().setTitle("맛집지도");
                        drawerLayout.closeDrawer(main_drawer);
                        break;
                    case R.id.nav_keep:
                        transaction.replace(R.id.main_content,foodKeepFragment).commit();
                        getSupportActionBar().setTitle("맛집 즐겨찾기");
                        drawerLayout.closeDrawer(main_drawer);
                      break;
                    case R.id.nav_register:
                        intent=new Intent(MainActivity.this,RegisterActivity.class);
                        startActivityForResult(intent,200);
//                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawer(main_drawer);
                return false;
            }
        });
        onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(main_drawer)){
                    drawerLayout.closeDrawer(main_drawer);
                }else {
                    drawerLayout.openDrawer(main_drawer);
                }
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TextView name=findViewById(R.id.profile_name);
        preferences=getSharedPreferences("profile",AppCompatActivity.MODE_PRIVATE);
        editor=preferences.edit();
        name.setText(preferences.getString("name","OOO"));
        String strImage=preferences.getString("image","");
        if(!strImage.equals("")){
            CircleImageView icon=findViewById(R.id.profile_icon);
            icon.setImageBitmap(BitmapFactory.decodeFile(strImage));
        }
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content,foodListFragment).commit();
    }
    public void mClick(View v){
        switch (v.getId()){
            case R.id.profile_icon:
                Intent intent=new Intent(MainActivity.this,ProfileIconActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(main_drawer);
                break;
            case R.id.profile_name:
                intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(main_drawer);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                foodKeepFragment=new FoodKeepFragment();
                transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content, foodKeepFragment).commit();
                break;
            case 200:
                foodListFragment=new FoodListFragment();
                transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content, foodListFragment).commit();
                break;
        }
    }
}