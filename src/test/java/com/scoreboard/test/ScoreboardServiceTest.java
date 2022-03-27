package com.scoreboard.test;

import com.scoreboard.business.model.GameStats;
import com.scoreboard.factory.GamesFactory;
import com.scoreboard.repo.model.Game;
import com.scoreboard.service.FootballScoreboardService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreboardServiceTest {

    private FootballScoreboardService scoreboardService;

    @Before
    public void init(){
        scoreboardService = new FootballScoreboardService();
    }

    @Test
    public void testWhenNewGameStartedThenGameAddedToScoreboard(){
        Game game = GamesFactory.createNewGame("France", "Italy");
        GameStats gameStats = GamesFactory.fromGame(game);
        scoreboardService.registerNewGame(gameStats);
        Assert.assertNotNull(scoreboardService.getLiveScore(game.getGameId()));
    }

    @Test(expected = IllegalStateException.class)
    public void testWhenGameFinishedThenRemovedFromScoreboard(){
        Game game = GamesFactory.createNewGame("France", "Italy");
        GameStats gameStats = GamesFactory.fromGame(game);
        scoreboardService.registerNewGame(gameStats);
        scoreboardService.deRegisterGame(game.getGameId());

        //Having IllegalStateException means this game is no more available at scoreboard.
        scoreboardService.getLiveScore(game.getGameId());
    }

    @Test
    public void testUpdateGameScoreAndGetLiveScore() {
        Game game = GamesFactory.createNewGame("France", "Italy");
        game.updateScore(1,2);
        GameStats gameStats = GamesFactory.fromGame(game);
        scoreboardService.registerNewGame(gameStats);
        scoreboardService.updateGameScore(gameStats.getGameId(), game.getScore());
        String score = scoreboardService.getLiveScore(gameStats.getGameId());
        assertEquals("1 - 2", score);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenGameIdNullThenGetLiveScoreThrowsException(){
        scoreboardService.getLiveScore(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenGameIdNullThenUpdateScoreThrowsException(){
        scoreboardService.updateGameScore(null, "1 - 2");
    }
}
