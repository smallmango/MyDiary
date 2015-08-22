package com.mango.mydiary;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;

import com.mango.mydiary.db.MyDiaryDB;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {


    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCheckUser(){
        SQLiteDatabase db;
        MyDiaryDB diaryDB = MyDiaryDB.getInstance(this.getContext());
        //diaryDB.savaUser(new User(123,123));
        //diaryDB.saveDiary(new Diary("good",123));
        //assertEquals(true,diaryDB.checkUser(123,123));


    }

}