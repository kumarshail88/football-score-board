package com.scoreboard;

import com.scoreboard.service.FootballService;
import com.scoreboard.service.api.GameService;
import com.scoreboard.service.FootballGameSummaryService;
import com.scoreboard.service.FootballScoreboardService;
import com.scoreboard.service.api.GameSummaryService;
import com.scoreboard.service.api.ScoreboardService;

public class ScoreboardApp {
    public static void main(String[] args) {
        initApp();
    }

    public static GameService initApp(){
        ScoreboardService scoreboardService = new FootballScoreboardService();
        GameSummaryService gamesSummaryService = new FootballGameSummaryService();
        return new FootballService(scoreboardService, gamesSummaryService);
    }
}
