package com.mango.mydiary.model;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
public class User {

    private int id;
    private int password;

    public User(int id, int password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }


}
