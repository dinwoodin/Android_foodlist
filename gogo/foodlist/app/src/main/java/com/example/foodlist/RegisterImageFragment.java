package com.example.foodlist;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class RegisterImageFragment extends Fragment {

    String strFile;
    FoodVO vo = new FoodVO();
    ImageView image;
    FoodDB foodDB;
    FoodDAO foodDAO = new FoodDAO();
    String strImage="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register_image, container, false);

        vo=(FoodVO)getArguments().getSerializable("vo");
        image=view.findViewById(R.id.image);

        ImageView camera=view.findViewById(R.id.camera);
        Button prev=view.findViewById(R.id.prev);
        Button complete=view.findViewById(R.id.complete);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("vo", vo);
                ((RegisterActivity)getActivity()).replaceFragment("input", bundle);
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box=new AlertDialog.Builder(getContext());
                box.setMessage("저장하시겠습니까?");
                box.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FoodDB helper=new FoodDB(getContext());
                        foodDAO.insert(helper,vo);
                        getActivity().finish();
                    }
                });
                box.setNegativeButton("no",null);
                box.show();
            }
        });

        return view;
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Cursor cursor = getContext().getContentResolver().query(data.getData(), null, null, null, null);
        cursor.moveToFirst();
        strImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        image.setImageBitmap(BitmapFactory.decodeFile(strImage));
        strFile=strImage.substring(strImage.lastIndexOf("/")+1);
        System.out.println("123...;;;..;;;;;;;;;;;;"+strFile);
        cursor.close();
        vo.setImage(strFile);

    }
}