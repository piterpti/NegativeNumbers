package com.example.ruzik.liczbyujemne.Classes;

/**
 * Created by Piter on 02/06/2016.
 */
import android.util.Log;

import java.util.*;

public class GameStatus {

    private int currentQuestion = 0;
    private DifficultLevel difficultLevel;
    private ArrayList<Question> questions;

    public GameStatus(DifficultLevel level) {
        currentQuestion = -1;
        difficultLevel = level;
    }


    public DifficultLevel getDifficultLevel() {
        return difficultLevel;
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

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
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
}
