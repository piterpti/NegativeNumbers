package com.example.ruzik.liczbyujemne.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;

import com.example.ruzik.liczbyujemne.Classes.AnswerButton;
import com.example.ruzik.liczbyujemne.Classes.DifficultLevel;
import com.example.ruzik.liczbyujemne.Classes.GameStatus;
import com.example.ruzik.liczbyujemne.Classes.Question;
import com.example.ruzik.liczbyujemne.MainActivity;
import com.example.ruzik.liczbyujemne.R;

import layout.GameEnded;

public class GameFragment extends Fragment
{

    private final int VERDICT_TIME = 1000;

    private Button goBackMenuButton;
    private AnswerButton [] answerButtons;
    private TextView gameQuestionTextView;
    private Question currentQuestion;
    private TextView verdictTextView;
    private TextView progressTaskTextView;
    private ProgressBar progressBar;
    private CountDownTimer answerTimer;
    private String [] ANSWERS;

    private boolean nextQuestion = false;

    @Override
    public void onDestroy() {
        super.onDestroy();
        TurnOffTimer();
    }

    public GameFragment()
    {
        // required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        init(view);
        return view;
    }

    private void init(View view)
    {
        ANSWERS = new String[4];
        goBackMenuButton = (Button) view.findViewById(R.id.go_back_to_menu);
        goBackMenuButton.setOnClickListener(new MenuButtonListener());
        answerButtons = new AnswerButton[4];
        verdictTextView = (TextView) view.findViewById(R.id.verdictText);
        progressBar = (ProgressBar) view.findViewById(R.id.timeRemainProgressBar);
        progressTaskTextView = (TextView) view.findViewById(R.id.game_taskProgress);
        gameQuestionTextView = (TextView) view.findViewById(R.id.gameQuestion);
        answerButtons[0] = (AnswerButton) view.findViewById(R.id.game_answerButton1);
        answerButtons[1] = (AnswerButton) view.findViewById(R.id.game_answerButton2);
        answerButtons[2] = (AnswerButton) view.findViewById(R.id.game_answerButton3);
        answerButtons[3] = (AnswerButton) view.findViewById(R.id.game_answerButton4);

        for(AnswerButton answerButton : answerButtons)
        {
            answerButton.setOnClickListener(new AnswerButtonHandler());
        }
        LoadQuestion();
    }

    public void CreateGame()
    {
        DifficultLevel tempLevel = MainActivity.gameStatus.getDifficultLevel();
        MainActivity.gameStatus = new GameStatus(tempLevel);
        MainActivity.gameStatus.generateQuestionForNewGame();
        nextQuestion = true;
    }


    public void GameBreak()
    {
        TurnOffTimer();
        MenuFragment menuFragment = new MenuFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menuFragment);
        transaction.commit();
    }

    private void Verdict(boolean answer)
    {
        TurnOffTimer();
        nextQuestion = true;
        currentQuestion.setCorrectAnswer(answer);
        verdictTextView.setVisibility(View.VISIBLE);
        gameQuestionTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        if(answer) // GOOD ANSWER
        {
            verdictTextView.setText("GOOD!");
        }
        else
        {
            verdictTextView.setText("WRONG!");
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                verdictTextView.setVisibility(View.INVISIBLE);
                gameQuestionTextView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                LoadQuestion();
            }
        }, VERDICT_TIME);
    }


    private void LoadQuestion()
    {
        if(nextQuestion)
        {
            if(MainActivity.gameStatus.getQuestion(true) == null)
            {
                GameEnd();
                return;
            }
            CreateTimer(MainActivity.gameStatus.getDifficultLevel().getAnswerTime() * 1000);
            nextQuestion = false;
        }
        currentQuestion = MainActivity.gameStatus.getQuestion(false);
        gameQuestionTextView.setText(currentQuestion.toString());
        progressTaskTextView.setText(MainActivity.gameStatus.getCurrentQuestionNum() + "/" + MainActivity.gameStatus.getDifficultLevel().getQuestionCount());
        ANSWERS = currentQuestion.getAnswers();
        ShuffleArray(ANSWERS);
        for(int i = 0; i < answerButtons.length; i++)
        {
            answerButtons[i].setText(ANSWERS[i]);
        }
    }

    private void ShuffleArray(String[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private void GameEnd()
    {
        GameEnded gameEnded = new GameEnded();
        try {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container, gameEnded);
            transaction.commit();
        }
        catch (NullPointerException e) {
            Log.w("NullPointer", "Controlled null pointer exception: " + e);
        }
    }

    private void CreateTimer(int time) {
        TurnOffTimer();
        progressBar.setProgress(100);
        answerTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                float ppp = millisUntilFinished / MainActivity.gameStatus.getDifficultLevel().getAnswerTime() / 10;
                progressBar.setProgress((int) ppp);
            }

            @Override
            public void onFinish() {
                Verdict(false);
            }
        };
        answerTimer.start();
    }

    private void TurnOffTimer()
    {
        if(answerTimer != null) {
            answerTimer.cancel();
            answerTimer = null;
        }
    }

    private class AnswerButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            if(currentQuestion.checkAnswer(((AnswerButton)v).getText().toString()))
            {
                Verdict(true);
            }
            else
            {
                Verdict(false);
            }
        }
    }

    public boolean EndGameDialogShow()
    {

        AlertDialog show = new AlertDialog.Builder(getContext())
                .setTitle("Tytul")
                .setMessage("Wiadomosc")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameBreak();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;
    }

    private class MenuButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            EndGameDialogShow();
        }
    }
}
