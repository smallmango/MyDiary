package com.mango.mydiary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.mango.mydiary.R;
import com.mango.mydiary.db.MyDiaryDB;
import com.mango.mydiary.model.Diary;
import com.mango.mydiary.model.User;
import com.mango.mydiary.util.DiaryAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/8/22 0022.
 */
public class DiaryList extends Activity implements View.OnClickListener{

    private MyDiaryDB diaryDB;
    private Context mContext;
    private List<Diary> diaryList;
    private DiaryAdapter diaryAdapter;
    private ListView listView;
    private Button writeDiary;
    private static final int FIRST= 1; //第一次获取数据库
    private static final int SECOND= 2; //更新数据库(更新Listview)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.diary_list);
        mContext=getApplicationContext();
        //Log.i("look","haha");
        init(); //初始化控件,添加点击事件
        getData(FIRST); //根据用户名去从数据库中获取日志
        diaryAdapter = new DiaryAdapter(mContext,R.layout.diary_item,diaryList);
        listView= (ListView) findViewById(R.id.diaryListView);
        listView.setAdapter(diaryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Diary diary = diaryList.get(position); //获取到点击对象
                String diaryTitle = diary.getName();
                Intent intent = new Intent(mContext, DiaryText.class); //跳转到日记内容页面
                Bundle bundle = new Bundle();
                bundle.putString("diaryTitle", diaryTitle);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void init() {

        writeDiary= (Button) findViewById(R.id.writeDiary);
        writeDiary.setOnClickListener(this);
    }

    private void getData(int choose) {

        //Log.i("kao","oo");
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String userID= pref.getString("userID", "");
        String userPassword=pref.getString("userPassword","");
        diaryDB=MyDiaryDB.getInstance(mContext);
        //setData(userID, diaryDB); //设置测试数据
        if(choose==1) {
            diaryList = diaryDB.checkDiaryTitles(new User(userID,userPassword)); //获取返回对象
        }else{
            diaryList.addAll(diaryDB.checkDiaryTitles(new User(userID,userPassword)));
        }

    }

    //获取测试数据
    private void setData(String userID, MyDiaryDB diaryDB) {

        for(int i=0;i<10;i++){
            diaryDB.saveDiary(new Diary(i+1,"diary"+i+1,userID));
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.writeDiary:
                Intent intent = new Intent(this,EditDiary.class);
                startActivity(intent);
                break;
        }
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        updateDatabase(); //更新数据库并刷新
    }

    private void updateDatabase() {

        //Log.i("fuck", "shit");
        diaryList.clear(); //清除原数据
        getData(SECOND); //重新获取数据库数据
        diaryAdapter.notifyDataSetChanged();
    }
}
