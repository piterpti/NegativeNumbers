package com.example.ruzik.liczbyujemne.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ruzik.liczbyujemne.Classes.Achievement;
import com.example.ruzik.liczbyujemne.Classes.AchievementAdapter;
import com.example.ruzik.liczbyujemne.MainActivity;
import com.example.ruzik.liczbyujemne.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementFragment extends Fragment implements AdapterView.OnItemLongClickListener {

    private ListView achievementsListView;
    private AchievementAdapter achievementAdapter;


    public AchievementFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);
        init(view);
        return view;
    }

    private void init(View view)
    {
        achievementsListView = (ListView) view.findViewById(R.id.achievement_list);
        achievementAdapter = new AchievementAdapter(getActivity(), MainActivity.gameStatus.getAchievements());
        achievementsListView.setAdapter(achievementAdapter);
        achievementsListView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Achievement achievement = (Achievement) parent.getItemAtPosition(position);
        if( ! achievement.isLocked())
        {
            AlertDialog show = new AlertDialog.Builder(getContext())
                    .setTitle("Warning")
                    .setMessage("Do you want reset achievement?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String toDelete = achievement.getId() + ",";
                            String toSave = PreferenceManager.getDefaultSharedPreferences(MainActivity.CONTEXT).getString(MainActivity.ACHIEVEMENT_TAG, "");
                            toSave = toSave.replace(toDelete, "");
                            PreferenceManager.getDefaultSharedPreferences(MainActivity.CONTEXT).edit().putString(MainActivity.ACHIEVEMENT_TAG, toSave).commit();
                            achievementAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return true;
    }

}
