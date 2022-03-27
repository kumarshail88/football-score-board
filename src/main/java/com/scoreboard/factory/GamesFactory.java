package com.scoreboard.factory;

import com.scoreboard.business.model.GameStats;
import com.scoreboard.business.model.GameSummary;
import com.scoreboard.repo.model.Game;
import com.scoreboard.repo.model.Team;
import com.scoreboard.utils.GameIdGenerator;

import static com.scoreboard.validation.Validator.validateGameNotNull;
import static com.scoreboard.validation.Validator.validateNullOrEmptyString;

public class GamesFactory {

    private static final GameIdGenerator gameIdGenerator = new GameIdGenerator();

    private GamesFactory(){}

    public static Game createNewGame(String homeTeamName, String guestTeamName){
        validateNullOrEmptyString(homeTeamName, "homeTeamName");
        validateNullOrEmptyString(guestTeamName, "guestTeamName");

        Team homeTeam = new Team(homeTeamName);
        Team guestTeam = new Team(guestTeamName);
        return new Game(gameIdGenerator.getNextId(), homeTeam, guestTeam);
    }

    public static GameStats fromGame(Game game){
        GameStats gameStats = new GameStats();
        gameStats.setGameId(game.getGameId());
        gameStats.setGameStatus(game.getStatus());
        gameStats.setHomeTeam(game.getHomeTeam().getName());
        gameStats.setGuestTeam(game.getGuestTeam().getName());
        gameStats.setCurrentScore(game.getScore());
        return gameStats;
    }

    public static GameSummary createGameSummary(Game game){
        validateGameNotNull(game);
        GameSummary gameSummary = new GameSummary();
        gameSummary.setGameId(game.getGameId());
        gameSummary.setTotalScore(game.getTotalScore());
        gameSummary.setScoreSummary(game.getScoreSummary());
        return gameSummary;
    }
}
