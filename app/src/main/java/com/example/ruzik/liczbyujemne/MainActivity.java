package com.example.ruzik.liczbyujemne;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ruzik.liczbyujemne.Classes.DifficultLevel;
import com.example.ruzik.liczbyujemne.Classes.GameStatus;
import com.example.ruzik.liczbyujemne.Classes.Question;
import com.example.ruzik.liczbyujemne.fragments.AboutFragment;
import com.example.ruzik.liczbyujemne.fragments.DifficultLevelFragment;
import com.example.ruzik.liczbyujemne.fragments.GameFragment;
import com.example.ruzik.liczbyujemne.fragments.MenuFragment;

public class MainActivity extends FragmentActivity {

    public static final String DIFFICULT_LEVEL_KEY = "DIFFICULT_LEVEL";
    public static final String GAME_FRAGMENT_TAG = "GAME_FRAGMENT";


    public static GameStatus gameStatus;
    public static DifficultLevel[] difficultLevels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MenuFragment menuFragment = new MenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(savedInstanceState == null) {
            transaction.add(R.id.fragment_container, menuFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            LoadDifficultLevels();
            init();
        }
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

    @Override
    public void onBackPressed() {
        boolean callSuper = true;
        GameFragment gameFragment = null;

        gameFragment = (GameFragment) getSupportFragmentManager().findFragmentByTag(GAME_FRAGMENT_TAG);
        if (gameFragment != null && gameFragment.isVisible()) {
            if(gameFragment.EndGameDialogShow()) {
                callSuper = false;
            }
        }
        /*if(gameSummaryFragment != null && gameSummaryFragment.isVisible()) {
            gameSummaryFragment.BackToMenu();
            callSuper = false;
        }
        if(callSuper)
        {
            super.onBackPressed();
        }*/
    }
}
