package com.mango.mydiary.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mango.mydiary.R;
import com.mango.mydiary.db.MyDiaryDB;
import com.mango.mydiary.model.User;

/**
 * Created by Administrator on 2015/8/22 0022.
 */
public class Register extends Activity implements View.OnClickListener{

    private Context mContext;
    private MyDiaryDB diaryDb;
    private EditText userID;
    private EditText userPassword;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        init(); //初始化控件并添加点击事件
    }

    private void init() {

        userID= (EditText) findViewById(R.id.userID);
        userPassword= (EditText) findViewById(R.id.userPassword);
        register= (Button) findViewById(R.id.register);
        mContext=getApplicationContext();
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                String id = userID.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                diaryDb=MyDiaryDB.getInstance(mContext);
                diaryDb.savaUser(new User(id, password));
                Toast.makeText(mContext,"注册完成",Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}


