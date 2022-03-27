package com.scoreboard.test;

import com.scoreboard.business.model.GameSummary;
import com.scoreboard.factory.GamesFactory;
import com.scoreboard.repo.model.Game;
import com.scoreboard.repo.model.GameStatus;
import com.scoreboard.service.FootballGameSummaryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GamesSummaryServiceTest {

    private FootballGameSummaryService summaryService;
    @Before
    public void init(){
        summaryService = new FootballGameSummaryService();
    }

    @Test
    public void testWhenGameFinishedThenAddedToSummary(){
        Game game = GamesFactory.createNewGame("Brazil", "France");
        game.setStatus(GameStatus.FINISHED);
        GameSummary gameSummary = GamesFactory.createGameSummary(game);
        summaryService.addFinishedGame(gameSummary);
        Assert.assertTrue(summaryService.getGamesSummary().size() == 1);
    }

    @Test
    public void testWhenNoGameStartedOrFinishedThenGameSummaryEmpty(){
        Assert.assertTrue(summaryService.getGamesSummary().size() == 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testWhenGameNullThenGameNotAddedToSummaryAndThrowsException(){
        summaryService.addFinishedGame(null);
    }

}
