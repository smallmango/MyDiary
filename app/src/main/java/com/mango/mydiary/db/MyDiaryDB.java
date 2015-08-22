package com.mango.mydiary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mango.mydiary.model.Diary;
import com.mango.mydiary.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
public class MyDiaryDB {

    //数据库名称
    public static final String DB_NAME = "my_diary";

    //数据库版本
    public static final int VERSION = 1;

    private static MyDiaryDB myDiaryDB;

    private SQLiteDatabase db;

    /**
     * 构造方法私有化,获取数据库实例
     */
    private MyDiaryDB(Context context){

        MyDiaryOpenHelper dbHelper = new MyDiaryOpenHelper(context,DB_NAME,null,VERSION);

        db = dbHelper.getWritableDatabase();
        //dbHelper.deleteDatabase(context);
    }

    /**
     * 获取myDiaryDB实例
     */
    public static MyDiaryDB getInstance(Context context){
        if(myDiaryDB==null)
            myDiaryDB = new MyDiaryDB(context);
        return myDiaryDB;
    }

    /**
     * 保存（添加）新用户
     */
    public void savaUser(User user){

        if(user!=null){
            ContentValues values = new ContentValues();
            values.put("id",user.getId());
            values.put("user_password",user.getPassword());
            db.insert("User",null,values);
        }
    }

    /**
     * 查询用户是否存在
     */
    public boolean checkUser(int userID,int userPassword){

        Cursor  cursor = db.query("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {

                int ID = cursor.getInt(cursor.getColumnIndex("id"));
                int password = cursor.getInt(cursor.getColumnIndex("user_password"));
                if(ID==userID&&userPassword==password) return true;  //账号存在

            }while(cursor.moveToNext());
        }
        return false; //账号不存在
    }

    /**
     * 保存日志
     */
    public void saveDiary(Diary diary){

        if(diary!=null){
            ContentValues values = new ContentValues();
            //values.put("id",diary.getDiaryID());
            values.put("diary_name",diary.getName());
            values.put("user_id",diary.getUserId());
            db.insert("Diary",null,values);
        }
    }


    /**
     * 查询所有日记题目:用于装载ListView 未测试
     */
    public List<Diary> checkDiaryTitles(User user){

        List<Diary> diarys = new ArrayList<Diary>();
        Cursor cursor = db.query("Diary",null,"user_id=?",new String[]{String.valueOf(user.getId())},null,null,null);
        if(cursor.moveToFirst()){
            do {
                Diary diary = new Diary();
                String id=cursor.getString(cursor.getColumnIndex("id"));
                String diary_name = cursor.getString(cursor.getColumnIndex("diary_name"));
                int user_id= user.getId();

                diary.setDiaryID(Integer.parseInt(id));
                diary.setName(diary_name);
                diary.setUserId(user_id);

                diarys.add(diary);
            }while(cursor.moveToNext());
        }
        return diarys;
    }


}
