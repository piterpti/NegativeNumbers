package com.example.ruzik.liczbyujemne.Classes;

/**
 * Created by Piter on 03/06/2016.
 */
public class Achievement {

    private int id;
    private int correctAnswers;
    private String name;
    private int difficultLevel;
    private boolean locked;

    public Achievement(int id, int difficultLevel, String name, int correctAnswers) {
        this.id = id;
        this.correctAnswers = correctAnswers;
        this.name = name;
        this.difficultLevel = difficultLevel;
    }

    @Override
    public String toString() {
        return name;
    }
}
