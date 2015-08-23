package com.mango.mydiary.model;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
public class Diary {

    private int diaryID;
    private String name;
    private String userId;

    public Diary(){}
    public Diary(String name,String userId){

        this.name=name;
        this.userId=userId;
    }
    public Diary(int diaryID,String name, String userId) {

        this.diaryID=diaryID;
        this.name = name;
        this.userId = userId;
    }

    public int getDiaryID() {
        return diaryID;
    }

    public void setDiaryID(int diaryID) {
        this.diaryID = diaryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
