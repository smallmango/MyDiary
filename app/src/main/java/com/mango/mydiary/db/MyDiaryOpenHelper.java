package com.mango.mydiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
public class MyDiaryOpenHelper extends SQLiteOpenHelper {

    //建立用户表
    public static final String CREATE_USER = "create table User("
            + "id integer primary key ,"
            + "user_password integer )";

    //建立日志记录表
    public static final String CREATE_DIARY = "create table Diary("
            + "id integer primary key autoincrement,"
            + "diary_name text, "
            + "user_id integer )";


    public MyDiaryOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //这里一般用于初始化
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_DIARY);
        db.execSQL(CREATE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       //onCreate(db);

    }

    //删除数据库
    public void deleteDatabase(Context context){
        context.deleteDatabase("my_diary");
    }
}
