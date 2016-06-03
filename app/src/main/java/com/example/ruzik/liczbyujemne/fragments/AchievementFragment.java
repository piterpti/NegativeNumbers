package com.example.ruzik.liczbyujemne.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ruzik.liczbyujemne.MainActivity;
import com.example.ruzik.liczbyujemne.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementFragment extends Fragment {

    private ListView achievementsListView;


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
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.achievement_adapter, MainActivity.gameStatus.getAchievements());
        achievementsListView.setAdapter(adapter);
    }

}
