package com.scoreboard.validation;

import com.scoreboard.business.model.GameStats;
import com.scoreboard.business.model.GameSummary;
import com.scoreboard.repo.model.Game;
import com.scoreboard.repo.model.GameStatus;
import com.scoreboard.repo.model.Team;

public class Validator {

    public static void validateNullOrEmptyString(String value, String fieldName){
        if (value == null || value.isEmpty() || value.isBlank()){
            throw new IllegalArgumentException("Invalid input " + fieldName + " cannot be null or empty.");
        }
    }

    public static void validateGameIdNotNull(Long gameId){
        if (gameId == null){
            throw new IllegalArgumentException("Invalid input " + gameId + " cannot be null.");
        }
    }

    public static void validateScoreNotNegative(int score, String fieldName){
        if (score < 0){
            throw new IllegalArgumentException("Invalid input " + fieldName + " cannot be negative.");
        }
    }

    public static void validateGameNotNull(Game game, Long gameId) {
        if (game == null) {
            throw new IllegalStateException("Game not available with id " + gameId
                    + " found. Game is either not started or has already finished.");
        }
    }

    public static void validateGameNotNull(Game game) {
        if (game == null) {
            throw new IllegalStateException("Game instance is null.");
        }
    }

    public static void validateGameFinished(Game game){
        if (GameStatus.FINISHED != game.getStatus()){
            throw new IllegalStateException("Game cannot be added to summary while being live.");
        }
    }

    public static void validateGameStarted(GameStatus gameStatus){
        if (GameStatus.STARTED != gameStatus){
            throw new IllegalStateException("Game cannot be added to scoreboard until started.");
        }
    }

    public static void validateGame(Game game){
        validateGameNotNull(game);
        validateTeam(game.getHomeTeam());
        validateTeam(game.getGuestTeam());
    }

    public static void validateTeam(Team team){
        if (team == null || team.getName() == null || team.getName().isEmpty() || team.getName().isBlank()){
            throw new IllegalArgumentException("Null or invalid instance of team found. Team name missing.");
        }
    }

    public static void validateGameStatusNotNull(GameStatus gameStatus){
        if (gameStatus == null){
            throw new IllegalStateException("GameStatus cannot be null.");
        }
    }

    public static void validateGameStats(GameStats gameStats){
        if (gameStats == null){
            throw new IllegalStateException("Game statistic for running game cannot be null.");
        }

        validateGameIdNotNull(gameStats.getGameId());
        validateNullOrEmptyString(gameStats.getHomeTeam(), "homeTeam");
        validateNullOrEmptyString(gameStats.getGuestTeam(), "guestTeam");
        validateNullOrEmptyString(gameStats.getCurrentScore(), "currentScore");
        validateGameStatusNotNull(gameStats.getGameStatus());
    }

    public static void validateGameSummary(GameSummary gameSummary){
        if (gameSummary == null){
            throw new IllegalStateException("Game summary for finished game cannot be null.");
        }

        validateGameIdNotNull(gameSummary.getGameId());
        validateNullOrEmptyString(gameSummary.getScoreSummary(), "scoreSummary");
        validateTotalScoreNotNull(gameSummary.getTotalScore());
    }

    public static void validateTotalScoreNotNull(Integer totalScore){
        if (totalScore == null){
            throw new IllegalStateException("Total score in Game summary for finished game cannot be null.");
        }
    }
}
