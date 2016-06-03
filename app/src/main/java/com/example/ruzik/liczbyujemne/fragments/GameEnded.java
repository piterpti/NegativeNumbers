package com.example.ruzik.liczbyujemne.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.*;

import com.example.ruzik.liczbyujemne.Classes.Achievement;
import com.example.ruzik.liczbyujemne.Classes.AchievementAdapter;
import com.example.ruzik.liczbyujemne.MainActivity;
import com.example.ruzik.liczbyujemne.R;
import com.example.ruzik.liczbyujemne.fragments.AboutFragment;
import com.example.ruzik.liczbyujemne.fragments.MenuFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameEnded extends Fragment {

    private Button backButton;
    private TextView gameSummaryTextView;
    private String gameSummary;


    public GameEnded() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_game_ended, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        gameSummaryTextView = (TextView) view.findViewById(R.id.gameEnd_summary);
        gameSummary = getResources().getString(R.string.end_summary);
        backButton = (Button) view.findViewById(R.id.gameEnd_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMenu();
            }
        });

        int correctAnswers = MainActivity.gameStatus.getCorrectAnswers();
        int questionCount = MainActivity.gameStatus.getQuestionCount();
        String toDisplay = gameSummary + " " + correctAnswers + "/" + questionCount;
        float percentAnswers = ((float) correctAnswers / (float) questionCount) * 100f;
        toDisplay += " (" + String.format("%.2f", percentAnswers) + "%)";
        gameSummaryTextView.setText(toDisplay);
        MainActivity.gameStatus.CheckAchievements(correctAnswers);
        ArrayList<Achievement> unlockedAchievements = MainActivity.gameStatus.getUnlockedAchievementsByGame();
        if(unlockedAchievements.size() > 0)
        {
            ListView achievementsListView = (ListView) view.findViewById(R.id.end_unlockedAchievementsList);
            TextView achievementsTextView = (TextView) view.findViewById(R.id.end_achievementsTextView);
            achievementsListView.setVisibility(View.VISIBLE);
            achievementsTextView.setVisibility(View.VISIBLE);
            achievementsListView.setAdapter(new AchievementAdapter(MainActivity.CONTEXT, unlockedAchievements));
        }
    }

    public void BackToMenu()
    {
        MenuFragment menuFragment = new MenuFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menuFragment, MainActivity.MENU_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
