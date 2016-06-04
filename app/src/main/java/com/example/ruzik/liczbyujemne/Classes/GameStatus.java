package com.example.ruzik.liczbyujemne.Classes;

/**
 * Created by Piter on 02/06/2016.
 */
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.ruzik.liczbyujemne.MainActivity;

import java.util.*;

public class GameStatus {

    private int currentQuestion = 0;
    private DifficultLevel difficultLevel;
    private ArrayList<Question> questions;
    private ArrayList<Achievement> achievements;
    private ArrayList<Achievement> unlockedAchievementsByGame;

    public GameStatus(DifficultLevel level) {
        currentQuestion = -1;
        difficultLevel = level;
        achievements = new ArrayList<>();
        unlockedAchievementsByGame = new ArrayList<>();
    }


    public DifficultLevel getDifficultLevel() {
        return difficultLevel;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList<Achievement> achievements) {
        this.achievements = achievements;
    }

    public void setDifficultLevel(DifficultLevel difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public void generateQuestionForNewGame() {
        questions = new ArrayList<>();
        for(int i = 0; i < difficultLevel.getQuestionCount(); i++)
        {
            questions.add(Question.QuestionGenerator.generateQuestion());
        }
    }

    public int getCurrentQuestionNum() {
        return currentQuestion + 1;
    }

    public Question getQuestion(boolean next) {
        if(next)
            currentQuestion++;
        if(currentQuestion  < questions.size())
        {
            return questions.get(currentQuestion);
        }
        else
            return null;
    }

    public int getCorrectAnswers()
    {
        if(questions.size() < 1)
            return -1;
        int correctAnswers = 0;
        for(Question q : questions)
        {
            if(q.isCorrectAnswer())
            {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    public int getQuestionCount()
    {
        return difficultLevel.getQuestionCount();
    }

    public void LogAchievementsList()
    {
        for(Achievement a : achievements) {
            Log.d("blabla", a.toString());
        }
    }

    public void CheckAchievements(int correctAnswers)
    {
        for(Achievement a : achievements)
        {
            if(a.isLocked())
            {
                if(correctAnswers >= a.getCorrectAnswers())
                {
                    a.setLocked(false);
                    unlockedAchievementsByGame.add(a);
                    String toSave = PreferenceManager.getDefaultSharedPreferences(MainActivity.CONTEXT).getString(MainActivity.ACHIEVEMENT_TAG, "");
                    if(!toSave.contains(String.valueOf(a.getId())))
                    {
                        toSave += a.getId() + ",";
                        PreferenceManager.getDefaultSharedPreferences(MainActivity.CONTEXT).edit().putString(MainActivity.ACHIEVEMENT_TAG, toSave).commit();
                    }
                }
            }
        }
    }

    public ArrayList<Achievement> getUnlockedAchievementsByGame() {
        return unlockedAchievementsByGame;
    }
}
