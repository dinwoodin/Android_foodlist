package com.example.foodlist;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class FoodKeepFragment extends Fragment {
    FoodDAO dao=new FoodDAO();
    List<FoodVO> array= new ArrayList<>();
    FoodAdapter adapter;
    RecyclerView list;
    int type=1;
    StaggeredGridLayoutManager manager;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food_list, container, false);

        //데이터생성
        FoodDB helper=new FoodDB(getContext());
        array=dao.keepList(helper);
        System.out.println("..........갯수.........."+array.size());

        adapter=new FoodAdapter(getContext(),array,"keep");
        list=view.findViewById(R.id.list_food);
        list.setAdapter(adapter);

        manager=new StaggeredGridLayoutManager(type,StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(manager);

        //타입 변경 버튼을 클릭한 경우
        ImageView list_type=view.findViewById(R.id.list_type);
        list_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1){
                    type=2;
                    list_type.setImageResource(R.drawable.ic_list);
                }else {
                    type=1;
                    list_type.setImageResource(R.drawable.ic_list2);
                }
                manager=new StaggeredGridLayoutManager(type,StaggeredGridLayoutManager.VERTICAL);
                list.setLayoutManager(manager);
            }
        });


        return view;
    }
}