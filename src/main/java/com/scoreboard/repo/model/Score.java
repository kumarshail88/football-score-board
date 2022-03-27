package com.scoreboard.repo.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Score {

    private AtomicInteger currentScore;

    public Score(){
        currentScore = new AtomicInteger(0);
    }

    public void add(int val){
        currentScore.getAndAdd(val);
    }
}
