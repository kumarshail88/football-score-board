package com.scoreboard.service;

import com.scoreboard.business.model.GameStats;
import com.scoreboard.service.api.ScoreboardService;

import java.util.HashMap;
import java.util.Map;

import static com.scoreboard.validation.Validator.*;

public class FootballScoreboardService implements ScoreboardService {

    private Map<Long, GameStats> scoreboard;

    public FootballScoreboardService() {
        this.scoreboard = new HashMap<>();
    }

    public void registerNewGame(GameStats game) {
        validateGameStats(game);
        validateGameStarted(game.getGameStatus());
        scoreboard.put(game.getGameId(), game);
    }

    public void updateGameScore(Long gameId, String score) {
        validateGameIdNotNull(gameId);
        validateNullOrEmptyString(score, "score");
        GameStats gameStats = scoreboard.get(gameId);
        validateGameStats(gameStats);
        gameStats.setCurrentScore(score);
    }

    public String getLiveScore(Long gameId){
        validateGameIdNotNull(gameId);
        GameStats gameStats = scoreboard.get(gameId);
        validateGameStats(gameStats);
        return gameStats.getCurrentScore();
    }

    public void deRegisterGame(Long gameId) {
        validateGameIdNotNull(gameId);
        scoreboard.remove(gameId);
    }
}
