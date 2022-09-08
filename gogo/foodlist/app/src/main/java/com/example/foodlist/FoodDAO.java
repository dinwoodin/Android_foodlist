package com.example.foodlist;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    SQLiteDatabase db;





//등록
    public void insert(FoodDB helper, FoodVO vo) {
        db = helper.getWritableDatabase();
        String sql = "insert into tbl_food(name,tel,address,image,latitude,longitude,keep,description) values(";
        sql += "'" + vo.getName() + "',";
        sql += "'" + vo.getTel() + "',";
        sql += "'" + vo.getAddress() + "',";
        sql += "'" + vo.getImage() + "',";
        sql += vo.getLatitude() + ",";
        sql += vo.getLongitude() + ",";
        sql += "0,";
        sql += "'" + vo.getDescription() + "')";
        db.execSQL(sql);


    }
    //맛집정보
    @SuppressLint("Range")
    public FoodVO read(FoodDB helper, int id){
        db=helper.getWritableDatabase();
        FoodVO vo=new FoodVO();
        String sql="select*from tbl_food where _id="+id;
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToNext()){
            vo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            vo.setName(cursor.getString(cursor.getColumnIndex("name")));
            vo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            vo.setTel(cursor.getString(cursor.getColumnIndex("tel")));
            vo.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            vo.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            vo.setImage(cursor.getString(cursor.getColumnIndex("image")));
            vo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            vo.setKeep(cursor.getInt(cursor.getColumnIndex("keep")));

        }
        return vo;
    }


    //즐겨찾기 추가및삭제
    public void updateKeep(FoodDB helper, int keep,int id){
       db=helper.getWritableDatabase();
        String sql="update tbl_food set keep= "+keep+" where _id="+id;
        db.execSQL(sql);
    }

    //즐겨찾기 목록
    @SuppressLint("Range")
    public List<FoodVO> keepList(FoodDB foodDB) {
        SQLiteDatabase db=foodDB.getReadableDatabase();
        List<FoodVO> array = new ArrayList<>();
        String sql="select * from tbl_food where keep=1 order by _id desc";
        Cursor cursor=db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            FoodVO vo=new FoodVO();
            vo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            vo.setName(cursor.getString(cursor.getColumnIndex("name")));
            vo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            vo.setTel(cursor.getString(cursor.getColumnIndex("tel")));
            vo.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            vo.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            vo.setImage(cursor.getString(cursor.getColumnIndex("image")));
            vo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            vo.setKeep(cursor.getInt(cursor.getColumnIndex("keep")));
            array.add(vo);
        }
        return array;
    }

    //Food All 목록 출력
    @SuppressLint("Range")
    public List<FoodVO> foodList(FoodDB foodDB) {
        SQLiteDatabase db=foodDB.getReadableDatabase();
        List<FoodVO> array = new ArrayList<>();
        String sql="select * from tbl_food order by _id desc";
        Cursor cursor=db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            FoodVO vo=new FoodVO();
            vo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            vo.setName(cursor.getString(cursor.getColumnIndex("name")));
            vo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            vo.setTel(cursor.getString(cursor.getColumnIndex("tel")));
            vo.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            vo.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            vo.setImage(cursor.getString(cursor.getColumnIndex("image")));
            vo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            vo.setKeep(cursor.getInt(cursor.getColumnIndex("keep")));
            array.add(vo);
        }
        return array;
    }
}

