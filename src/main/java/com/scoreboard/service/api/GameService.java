package com.scoreboard.service.api;

public interface GameService {
    Long startGame(String hostTeam, String guestTeam);
    void updateGameScore(Long gameId, int homeTeamScore, int guestTeamScore);
    void endGame(Long gameId);
    ScoreboardService getScoreboardService();
    GameSummaryService getSummaryService();
}
