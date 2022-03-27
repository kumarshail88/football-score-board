package com.scoreboard.service;

import com.scoreboard.business.model.GameStats;
import com.scoreboard.business.model.GameSummary;
import com.scoreboard.repo.model.Game;
import com.scoreboard.repo.model.GameStatus;
import com.scoreboard.service.api.GameService;
import com.scoreboard.service.api.GameSummaryService;
import com.scoreboard.service.api.ScoreboardService;

import java.util.concurrent.ConcurrentHashMap;

import static com.scoreboard.factory.GamesFactory.*;
import static com.scoreboard.validation.Validator.*;

public class FootballService implements GameService {

    private ScoreboardService scoreboardService;
    private GameSummaryService gamesSummaryService;
    private ConcurrentHashMap<Long, Game> games;

    public FootballService(ScoreboardService scoreboardService, GameSummaryService gamesSummaryService){
        games = new ConcurrentHashMap<>();
        this.scoreboardService = scoreboardService;
        this.gamesSummaryService = gamesSummaryService;
    }

    public synchronized Long startGame(String hostTeam, String guestTeam){
        Game game = createNewGame(hostTeam, guestTeam);
        games.putIfAbsent(game.getGameId(), game);
        GameStats gameStats = fromGame(game);
        scoreboardService.registerNewGame(gameStats);
        return game.getGameId();
    }

    public synchronized void updateGameScore(Long gameId, int homeTeamScore, int guestTeamScore) {
        validateGameIdNotNull(gameId);
        validateScoreNotNegative(homeTeamScore, "homeTeamScore");
        validateScoreNotNegative(guestTeamScore, "guestTeamScore");
        Game game = games.get(gameId);
        validateGameNotNull(game, gameId);
        game.updateScore(homeTeamScore, guestTeamScore);
        scoreboardService.updateGameScore(gameId, game.getScore());
    }

    public synchronized void endGame(Long gameId){
        validateGameIdNotNull(gameId);
        Game game = games.get(gameId);
        validateGame(game);
        game.setStatus(GameStatus.FINISHED);
        scoreboardService.deRegisterGame(gameId);
        GameSummary gameSummary = createGameSummary(game);
        gamesSummaryService.addFinishedGame(gameSummary);
    }

    @Override
    public ScoreboardService getScoreboardService() {
        return scoreboardService;
    }

    @Override
    public GameSummaryService getSummaryService() {
        return gamesSummaryService;
    }

}
