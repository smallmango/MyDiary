package com.mango.mydiary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mango.mydiary.R;
import com.mango.mydiary.db.MyDiaryDB;
import com.mango.mydiary.model.Diary;
import com.mango.mydiary.model.User;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2015/8/22 0022.
 */
public class EditDiary extends Activity implements View.OnClickListener {

    private Context mContext;
    private MyDiaryDB diaryDB;
    private User user;
    private EditText diaryTitle;
    private EditText diaryText;
    private Button saveDiary;
    private Button addPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_diary);
        init(); //初始化控件并添加点击事件
        getUser(); //获取用户对象

    }

    private void getUser() {

        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String userID = pref.getString("userID", "");
        String userPassword = pref.getString("userPassword", "");
        user = new User();
        user.setId(userID); //为全局对象赋值
        user.setPassword(userPassword);
    }

    private void init() {

        diaryTitle = (EditText) findViewById(R.id.diaryTitle);
        diaryText = (EditText) findViewById(R.id.diaryText);
        saveDiary = (Button) findViewById(R.id.savaDiary);
        addPhoto = (Button) findViewById(R.id.addPhoto);
        mContext = getApplicationContext();
        diaryDB = MyDiaryDB.getInstance(mContext);
        saveDiary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.savaDiary:
                String title = diaryTitle.getText().toString().trim(); //获取标题
                String text = diaryText.getText().toString(); //获取日志内容
                if(TextUtils.isEmpty(title)||TextUtils.isEmpty(text)) {
                    Toast.makeText(mContext, "上传格式不对", Toast.LENGTH_LONG).show();
                }else {
                    if(isPresence(title)) { //查看数据库中是否有这个题目了,约定题目不同重复
                        Toast.makeText(mContext, "该标题已存在,请修改", Toast.LENGTH_LONG).show();
                    }else {
                        saveTitle(title); //将日志题目保存到数据库
                        saveText(title, text); //将日志内容保存到文件
                        Toast.makeText(mContext, "上传成功", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                break;
        }
    }

    private boolean isPresence(String title) {

        return diaryDB.titleIsPresence(user,title);
    }

    //根据日记名字来保存内容,取出来也要根据题目.因此日记题目不能重复
    private void saveText(String title, String text) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(title, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //将日志标题保存到数据库
    private void saveTitle(String title) {

        diaryDB.saveDiary(new Diary(title, user.getId()));
    }
}
