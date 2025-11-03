package com.example.tp2;

import java.time.LocalTime;

public class Score {

//    private LocalTime heureLocale;
    private int score;

//    public LocalTime getHeureLocale() {
//        return heureLocale;
//    }
//
//    public void setHeureLocale(LocalTime heureLocale) {
//        this.heureLocale = heureLocale;
//    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Score(int score) {
//        this.heureLocale = heureLocale;
        this.score = score;
    }
}
