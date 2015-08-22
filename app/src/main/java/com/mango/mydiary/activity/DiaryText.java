package com.mango.mydiary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.mango.mydiary.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2015/8/22 0022.
 */
public class DiaryText extends Activity {

    private TextView showTitle;
    private TextView showText;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.diary_text);
        init(); //初始化控件
        title = getDiaryTitle(); //获取到日记标题,根据这个标题从文件中获取内容
        getDiaryText(title); //获取日记内容并显示,顺便显示题目
    }

    private void getDiaryText(String title) {

            FileInputStream in = null;
            BufferedReader reader = null;
            StringBuilder content = new StringBuilder();
            try {
                in = openFileInput(title);
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        String diaryText=content.toString();
        showText.setText(diaryText);
        showTitle.setText(title);
    }

    private void init() {
        showText = (TextView) findViewById(R.id.showText);
        showTitle = (TextView) findViewById(R.id.showTitle);
    }

    private String getDiaryTitle() {

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("diaryTitle");
        return title;
    }
}
