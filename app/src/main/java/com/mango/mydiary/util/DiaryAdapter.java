package com.mango.mydiary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.mydiary.R;
import com.mango.mydiary.model.Diary;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2015/8/22 0022.
 */
public class DiaryAdapter extends ArrayAdapter<Diary> {

    private int resource;
    private Bitmap bitmap;
    private TextView diaryName;
    private ImageView diaryImage;

    public DiaryAdapter(Context context, int resource, List<Diary> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Diary diary = getItem(position); //获取日志对象
        View view;
        if (convertView == null) { //未曾加载过
            view = LayoutInflater.from(getContext()).inflate(resource, null); //获取view对象
        } else {
            view = convertView;
        }
        diaryName = (TextView) view.findViewById(R.id.diaryName);
        diaryImage = (ImageView) view.findViewById(R.id.diaryImage);
        diaryName.setText(diary.getName());
        getImage(diary.getName());
        diaryImage.setImageBitmap(bitmap);
        return view;
    }


    private void getImage(String title) {

        try {
            if (android.os.Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED)) {
                //路径一定要拼对来,不要加后缀png啥的
                String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + title +"image";
                File file = new File(imagePath);
                bitmap = BitmapFactory.decodeFile(imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
