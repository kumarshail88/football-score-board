package com.scoreboard.utils;

import java.util.concurrent.atomic.AtomicLong;

public class GameIdGenerator {

    private final AtomicLong gameIdCounter;

    public GameIdGenerator(){
        gameIdCounter = new AtomicLong(0);
    }

    public Long getNextId(){
        return gameIdCounter.incrementAndGet();
    }
}
