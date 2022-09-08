package com.example.foodlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FoodInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    FoodDAO dao=new FoodDAO();
    FoodVO vo=new FoodVO();
    FoodAdapter adapter;
    ImageView image, keep;
    TextView name, address, description,tel;
    private GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        getSupportActionBar().setTitle("맛집정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image=findViewById(R.id.image);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        keep=findViewById(R.id.keep);
        description=findViewById(R.id.description);
        tel=findViewById(R.id.tel);


        Intent intent=getIntent();
        FoodDB helper=new FoodDB(this);
        vo=dao.read(helper,intent.getIntExtra("id",0));

        //갤러리 경로 가지고 오는 것
        String sdPath= Environment.getExternalStorageDirectory().getAbsolutePath();
        String strImage=sdPath+ "/Pictures/"+vo.getImage();
        //지도가지고 오는것 (map생성시 Activity에 포함되어있음)
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        image.setImageBitmap(BitmapFactory.decodeFile(strImage));
        name.setText(vo.getName());
        address.setText(vo.getAddress());
        description.setText(vo.getDescription());
        tel.setText(vo.getTel());
        if(vo.getKeep()==1){
            keep.setImageResource(R.drawable.ic_keep_on);
        }else {
            keep.setImageResource(R.drawable.ic_keep_off);
        }

        AlertDialog.Builder box=new AlertDialog.Builder(this);
        if(vo.getKeep()==1) keep.setImageResource(R.drawable.ic_keep_on);
        else keep.setImageResource(R.drawable.ic_keep_off);
        //즐겨찾기 버튼을 클릭한 경우
        ImageView keep=findViewById(R.id.keep);
        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vo.getKeep()==1){
                    keep.setImageResource(R.drawable.ic_keep_on);

                    box.setMessage("즐겨찾기취소");
                    box.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.updateKeep(helper,0,vo.getId());
                            keep.setImageResource(R.drawable.ic_keep_off);
                            vo.setKeep(0);

                        }
                    });
                    box.show();

                }else {
                    keep.setImageResource(R.drawable.ic_keep_off);

                    box.setMessage("즐겨찾기등록");
                    box.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.updateKeep(helper,1,vo.getId());
                            keep.setImageResource(R.drawable.ic_keep_on);
                            vo.setKeep(1);
                        }
                    });
                    box.show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        LatLng latLng=new LatLng(vo.getLatitude(),vo.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title(vo.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
    }


}