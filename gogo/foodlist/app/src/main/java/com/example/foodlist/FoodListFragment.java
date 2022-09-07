package com.example.foodlist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FoodListFragment extends Fragment {
    SQLiteDatabase db;
    SQLiteOpenHelper helper;
    Cursor cursor;
    FoodAdapter adapter;
    RecyclerView list;
    int type=1;
    List<FoodVO> array=new ArrayList<>();
    FoodVO vo=new FoodVO();
    FoodDAO dao=new FoodDAO();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food_list, container, false);
        helper=new FoodDB(getContext());
        db=helper.getWritableDatabase();

        String sql="select * from tbl_food order by _id desc";
        cursor=db.rawQuery(sql,null);
        FoodDB foodDB = new FoodDB(getContext());
        array=dao.foodList(foodDB);





        list=view.findViewById(R.id.list_food);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(type,StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
        adapter=new FoodAdapter(getContext(),cursor);
        list.setAdapter(adapter);

        return view;
    }
    class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{

        public FoodAdapter(Context context, Cursor cursor) {
        }

        @NonNull
        @Override
        public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=getLayoutInflater().inflate(R.layout.item_food,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
            FoodVO vo=array.get(position);
            holder.name.setText(vo.getName());
            holder.description.setText(vo.getDescription());
            String image="/storage/emulated/0/Pictures/"+vo.getImage();
            holder.image.setImageBitmap(BitmapFactory.decodeFile(image));
            if(vo.getKeep()==1){
                holder.keep.setImageResource(R.drawable.ic_keep_on);
            }else {
                holder.keep.setImageResource(R.drawable.ic_keep_off);
            }
        }

        @Override
        public int getItemCount() {
            return array.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image,keep;
            TextView name,description;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.image);
                name=itemView.findViewById(R.id.name);
                description=itemView.findViewById(R.id.description);
                keep=itemView.findViewById(R.id.keep);

            }
        }
    }
}