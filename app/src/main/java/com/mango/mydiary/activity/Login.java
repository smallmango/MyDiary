package com.mango.mydiary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mango.mydiary.R;
import com.mango.mydiary.db.MyDiaryDB;

/**
 * Created by Administrator on 2015/8/22 0022.
 */
public class Login extends Activity implements View.OnClickListener{

    private Context mContext;
    private MyDiaryDB diaryDb;
    private EditText userID;
    private EditText userPassword;
    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        init(); //初始化控件并添加点击事件
    }

    private void init() {

        userID= (EditText) findViewById(R.id.userID);
        userPassword= (EditText) findViewById(R.id.userPassword);
        login= (Button) findViewById(R.id.login);
        register= (Button) findViewById(R.id.register);
        mContext=getApplicationContext();
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String id = userID.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                diaryDb= MyDiaryDB.getInstance(mContext);
                if(diaryDb.checkUser(Integer.parseInt(id),Integer.parseInt(password))){ //账户存在
                    SharedPreferences.Editor editor = getSharedPreferences("user",MODE_PRIVATE).edit(); //对账户进行存储
                    editor.putString("userID",id);
                    editor.putString("userPassword",password);
                    editor.commit();
                    Intent intent = new Intent(mContext,DiaryList.class);
                    startActivity(intent);
                }else { //账户不存在
                    Toast.makeText(mContext,"账号不存在或有误",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.register:
                Intent intent = new Intent(this,Register.class);
                startActivity(intent);
                break;
        }

    }
}
