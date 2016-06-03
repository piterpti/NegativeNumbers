package com.example.ruzik.liczbyujemne.Classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ruzik.liczbyujemne.R;

public class DifficultLevelAdapter extends ArrayAdapter {

    public static final int ACTIVE_DIFFICULT_LEVEL = Color.RED;
    public static final int INACTIVE_DIFFICULT_LEVEL = Color.WHITE;


    public DifficultLevelAdapter(Context context, DifficultLevel[] list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DifficultLevel lvl = (DifficultLevel) getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.difficulty_levels_adapter, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.diffLevelText);
        TextView countView = (TextView) convertView.findViewById(R.id.diffLevelCount);
        TextView timeView = (TextView) convertView.findViewById(R.id.diffLevelTime);
        textView.setText(lvl.getLevelName());
        timeView.setText(convertView.getResources().getString(R.string.diffAdapter_anwerTime) + " " + lvl.getAnswerTime() + " seconds");
        countView.setText(convertView.getResources().getString(R.string.diffAdapter_questionCount) + " " + lvl.getQuestionCount());
        if(!lvl.isActive())
        {
            convertView.setBackgroundColor(INACTIVE_DIFFICULT_LEVEL);
        } else {
            convertView.setBackgroundColor(ACTIVE_DIFFICULT_LEVEL);
        }
        return convertView;
    }
}
