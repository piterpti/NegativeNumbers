package com.example.ruzik.liczbyujemne.Classes;

/**
 * Created by Piter on 02/06/2016.
 */
import android.util.Log;

import java.util.Random;

public class Question {

    enum Sign {
        ADD, DIFF;
    }

    private int firstNumber;
    private int secondNumber;
    private Sign actionSign;
    private boolean correctAnswer = false;

    public Question(int firstNumber, int secondNumber, Sign actionSign) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.actionSign = actionSign;
    }

    public boolean checkAnswer(String answer) {
        String correctAnswer = "";
        if(actionSign == Sign.ADD)
            correctAnswer = String.valueOf(firstNumber + secondNumber);
        else
            correctAnswer = String.valueOf(firstNumber - secondNumber);
        if(answer.equals(correctAnswer))
            return true;
        else
            return false;
    }

    public String getAnswer1()
    {
        return String.valueOf(firstNumber + secondNumber);
    }

    public String getAnswer2()
    {
        return String.valueOf(firstNumber - secondNumber);
    }

    public String getAnswer3()
    {
        return String.valueOf(-firstNumber + secondNumber);
    }

    public String getAnswer4()
    {
        return String.valueOf(-firstNumber - secondNumber);
    }

    public String[] getAnswers()
    {
        return new String []{getAnswer1(),
                getAnswer2(),
                getAnswer3(),
                getAnswer4()};
    }


    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        String sign = actionSign == Sign.ADD ? " + " : " - ";
        String completeQuestion = firstNumber + sign;
        if(secondNumber < 0)
        {
            completeQuestion += "(";
            completeQuestion += secondNumber + ")";
        }
        else
        {
            completeQuestion += secondNumber + "";
        }

        return  completeQuestion;
    }

    public static class QuestionGenerator
    {
        public static Question generateQuestion()
        {
            Random generator = new Random();
            int firstNum = generator.nextInt(9) + 1;
            int pow = generator.nextInt(3);
            firstNum = firstNum * (int)Math.pow(10, pow);
            firstNum = generator.nextBoolean() ? -firstNum : firstNum;

            int secondNum = generator.nextInt(9) + 1;
            pow = generator.nextInt(3);
            secondNum = secondNum* (int)Math.pow(10, pow);
            secondNum = generator.nextBoolean() ? -secondNum : secondNum;

            Sign sign = generator.nextBoolean() ? Sign.ADD : Sign.DIFF;
            return new Question(firstNum, secondNum, sign);
        }
    }
}
