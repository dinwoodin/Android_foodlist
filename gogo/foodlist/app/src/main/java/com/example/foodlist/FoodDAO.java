package com.example.foodlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    SQLiteDatabase db;

    //Food All 목록 출력
    public List<FoodVO> foodList(FoodDB foodDB) {
        SQLiteDatabase db=foodDB.getReadableDatabase();
        List<FoodVO> array = new ArrayList<>();
        String sql="select * from tbl_food order by _id desc";
        Cursor cursor=db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            FoodVO vo=new FoodVO();
            vo.setId(cursor.getInt(0));
            vo.setName(cursor.getString(1));
            vo.setAddress(cursor.getString(2));
            vo.setTel(cursor.getString(3));
            vo.setLatitude(cursor.getDouble(4));
            vo.setLongitude(cursor.getDouble(5));
            vo.setImage(cursor.getString(6));
            vo.setDescription(cursor.getString(7));
            vo.setKeep(cursor.getInt(8));
            array.add(vo);
        }
        return array;
    }
}

