package com.scoreboard.service.api;

import com.scoreboard.business.model.GameStats;

public interface ScoreboardService {
    void registerNewGame(GameStats game);
    void updateGameScore(Long gameId, String score);
    String getLiveScore(Long gameId);
    void deRegisterGame(Long gameId);
}
