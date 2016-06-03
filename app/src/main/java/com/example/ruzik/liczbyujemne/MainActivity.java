package com.example.ruzik.liczbyujemne;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

import com.example.ruzik.liczbyujemne.Classes.Achievement;
import com.example.ruzik.liczbyujemne.Classes.DifficultLevel;
import com.example.ruzik.liczbyujemne.Classes.GameStatus;
import com.example.ruzik.liczbyujemne.fragments.AboutFragment;
import com.example.ruzik.liczbyujemne.fragments.AchievementFragment;
import com.example.ruzik.liczbyujemne.fragments.DifficultLevelFragment;
import com.example.ruzik.liczbyujemne.fragments.GameFragment;
import com.example.ruzik.liczbyujemne.fragments.MenuFragment;

import com.example.ruzik.liczbyujemne.fragments.GameEnded;

public class MainActivity extends FragmentActivity {

    public static final String DIFFICULT_LEVEL_KEY = "DIFFICULT_LEVEL";
    public static final String GAME_FRAGMENT_TAG = "GAME_FRAGMENT";
    public static final String GAME_ENDED_TAG = "GAME_ENDED";
    public static final String MENU_TAG  = "MENU";
    public static final String ACHIEVEMENT_TAG = "ACHIEVEMENT";

    public static String ACHIEVEMENT;
    public static Context CONTEXT;


    public static GameStatus gameStatus;
    public static DifficultLevel[] difficultLevels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MenuFragment menuFragment = new MenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(savedInstanceState == null) {
            transaction.add(R.id.fragment_container, menuFragment, MENU_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
            LoadDifficultLevels();
            init();
            LoadAchievementsList();
        }
        CONTEXT = this;
    }

    public void ExitApplication(View view) {
        System.exit(0);
    }

    public void NewGame(View view) {
        GameFragment gameFragment = new GameFragment();
        gameFragment.setRetainInstance(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, gameFragment, GAME_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
        gameFragment.CreateGame();
        LoadAchievementsList();
        gameStatus.LogAchievementsList();
    }

    private void LoadDifficultLevels()
    {
        String [] difficultLevelsArray = getResources().getStringArray(R.array.DifficultLevels);
        difficultLevels = new DifficultLevel[difficultLevelsArray.length];
        int counter = 0;
        for(String diffLevel : difficultLevelsArray) {
            String [] temp = diffLevel.split(",");
            difficultLevels[counter] = new DifficultLevel(Integer.valueOf(temp[0]), temp[1], Integer.valueOf(temp[2]), Integer.valueOf(temp[3]));
            counter++;
        }
    }

    private void LoadAchievementsList()
    {
        String [] achievementsList = getResources().getStringArray(R.array.Achievements);
        ArrayList<Achievement> achievements = new ArrayList<>();
        for(String ach : achievementsList)
        {
            String [] temp = ach.split(",");
            if(Integer.valueOf(temp[1]) == gameStatus.getDifficultLevel().getId())
            {
                Achievement toAdd = new Achievement(Integer.valueOf(temp[0]), Integer.valueOf(temp[1]), temp[2], Integer.valueOf(temp[3]));
                achievements.add(toAdd);
            }
        }
        gameStatus.setAchievements(achievements);
        GetUnlockedAchievements();
    }

    private void GetUnlockedAchievements()
    {
        ACHIEVEMENT = PreferenceManager.getDefaultSharedPreferences(this).getString(ACHIEVEMENT_TAG, "");
        Log.d("blabla", "ACH: " + ACHIEVEMENT);
        String [] unlockedAchievements = ACHIEVEMENT.split(",");
        for(String a : unlockedAchievements)
        {
            if(a.length() > 0)
            {
                int id = Integer.parseInt(a);
                SetAchievementToActive(id);
            }
        }
    }

    private void SetAchievementToActive(int id)
    {
        for(Achievement a : gameStatus.getAchievements())
        {
            if(a.getId() == id)
            {
                a.setLocked(false);
            }
        }
    }

    private void init() {
        gameStatus = new GameStatus(null);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        int lastDiffLevelId = sharedPreferences.getInt(DIFFICULT_LEVEL_KEY, 1);
        for(DifficultLevel level : difficultLevels) {
            if(level.getId() == lastDiffLevelId) {
                gameStatus.setDifficultLevel(level);
            }
        }
    }

    public void ChooseDifficultLevel(View view) {
        DifficultLevelFragment difficultLevelFragment = new DifficultLevelFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, difficultLevelFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void AboutGame(View view) {
        AboutFragment aboutFragment = new AboutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, aboutFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void Achievements(View view) {
        AchievementFragment achievementFragment = new AchievementFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, achievementFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        LoadAchievementsList();
    }

    @Override
    public void onBackPressed() {
        MenuFragment menuFragment = null;
        menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag(MENU_TAG);
        if(menuFragment != null && menuFragment.isVisible()) {
            System.exit(0);
        }
        boolean callSuper = true;
        GameFragment gameFragment = null;
        GameEnded gameEnded = null;
        gameFragment = (GameFragment) getSupportFragmentManager().findFragmentByTag(GAME_FRAGMENT_TAG);
        gameEnded = (GameEnded) getSupportFragmentManager().findFragmentByTag(GAME_ENDED_TAG);
        if (gameFragment != null && gameFragment.isVisible()) {
            if(gameFragment.EndGameDialogShow()) {
                callSuper = false;
            }
        }
        if(gameEnded != null && gameEnded.isVisible()) {
            gameEnded.BackToMenu();
            callSuper = false;
        }
        if(callSuper)
        {
            super.onBackPressed();
        }
    }
}
