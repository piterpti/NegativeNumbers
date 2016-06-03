package com.example.ruzik.liczbyujemne.Classes;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.*;
import com.example.ruzik.liczbyujemne.R;

/**
 * Created by Piter on 03/06/2016.
 */
public class AchievementAdapter extends ArrayAdapter  {

    private final int LOCKED_COLOR = Color.GRAY;
    private final int UNLOCKED_COLOR = Color.GREEN;

    public AchievementAdapter(Context context, ArrayList<Achievement> list)
    {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Achievement achievement = (Achievement) getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.achievement_adapter, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.listItem);
        textView.setText(achievement.getName());
        if(achievement.isLocked())
        {
            textView.setTextColor(LOCKED_COLOR);
        }
        else
        {
            textView.setTextColor(UNLOCKED_COLOR);
        }
        return convertView;
    }


}
