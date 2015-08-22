package com.mango.mydiary.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mango.mydiary.R;
import com.mango.mydiary.model.Diary;

import java.util.List;

/**
 * Created by Administrator on 2015/8/22 0022.
 */
public class DiaryAdapter extends ArrayAdapter <Diary> {

    private int resource;

    public DiaryAdapter(Context context, int resource, List<Diary> objects) {
        super(context, resource, objects);
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Diary diary = getItem(position); //获取日志对象
        View view;
        if(convertView==null){ //未曾加载过
            view = LayoutInflater.from(getContext()).inflate(resource, null); //获取view对象
        }else{
            view=convertView;
        }
        TextView diaryName = (TextView) view.findViewById(R.id.diaryName);
        //ImageView diaryImage= (ImageView) view.findViewById(R.id.diaryImage);
        diaryName.setText(diary.getName());
        //diaryImage.setImageDrawable(R.drawable.ic_launcher);
        return view;
    }
}
