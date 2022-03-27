package com.scoreboard.utils;

import com.scoreboard.business.model.GameSummary;

import java.util.Comparator;

public class GameSummaryComparator implements Comparator<GameSummary> {
    @Override
    public int compare(GameSummary g1, GameSummary g2) {
        if (g1.getTotalScore() == g2.getTotalScore()){
            return g2.getGameId().compareTo(g1.getGameId());
        }
        return g1.getTotalScore() > g2.getTotalScore() ? -1 : 1;
    }
}
