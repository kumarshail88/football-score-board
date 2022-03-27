package com.scoreboard.test;

import com.scoreboard.ScoreboardApp;
import com.scoreboard.service.api.GameService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class GameServiceTest {

    private GameService command;

    @Before
    public void init() {
        command = ScoreboardApp.initApp();
    }

    @Test
    public void testStartNewGame() {
        Long gameId = command.startGame("Mexico", "Canada");
        assertNotNull(gameId);
    }

    @Test
    public void testUpdateGameScoreAndGetLiveScore() {
        Long gameId = command.startGame("Spain", "Brazil");
        assertNotNull(gameId);
        command.updateGameScore(gameId, 1, 1);
        assertEquals("1 - 1", command.getScoreboardService().getLiveScore(gameId));
    }

    @Test
    public void testWhenGameScoreUpdateConcurrentlyThenSuccess() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(50);
        Long gameId = command.startGame("Spain", "Brazil");
        for (int i = 1; i <= 50; i++){
            service.submit(() -> {
                command.updateGameScore(gameId, 1, 1);
                latch.countDown();
            });
        }

        latch.await();
        assertEquals("50 - 50", command.getScoreboardService().getLiveScore(gameId));
    }

    @Test
    public void testFinishGameAndGetGamesSummary() {
        Long gameId = command.startGame("Germany", "France");
        assertNotNull(gameId);
        command.endGame(gameId);
        List<String> gameSummary = command.getSummaryService().getGamesSummary();
        assertNotNull(gameSummary);
        assertEquals(1, gameSummary.stream()
                .filter(game -> game.equalsIgnoreCase("Germany 0 - France 0"))
                .count());
    }

    @Test
    public void testWhenBeginAndEndMultipleGamesThenRetrieveGameSummarySuccess() {
        Long mexicoCanada = command.startGame("Mexico", "Canada");
        Long spainBrazil = command.startGame("Spain", "Brazil");
        Long germanyFrance = command.startGame("Germany", "France");
        Long uruguayItaly = command.startGame("Uruguay", "Italy");
        Long argentinaAustralia = command.startGame("Argentina", "Australia");

        command.endGame(mexicoCanada);
        command.endGame(spainBrazil);
        command.endGame(germanyFrance);
        command.endGame(uruguayItaly);
        command.endGame(argentinaAustralia);

        List<String> gameSummary = command.getSummaryService().getGamesSummary();
        assertEquals(5, gameSummary.size());
    }

    @Test
    public void testWhenBeginMultipleGamesButEndFewThenRetrieveGameSummarySuccess() {
        Long mexicoCanada = command.startGame("Mexico", "Canada");
        Long spainBrazil = command.startGame("Spain", "Brazil");
        Long germanyFrance = command.startGame("Germany", "France");
        Long uruguayItaly = command.startGame("Uruguay", "Italy");
        Long argentinaAustralia = command.startGame("Argentina", "Australia");

        command.endGame(mexicoCanada);
        command.endGame(spainBrazil);
        command.endGame(germanyFrance);

        List<String> gameSummary = command.getSummaryService().getGamesSummary();
        assertEquals(3, gameSummary.size());
    }

    @Test
    public void testWhenGameNotFinishedThenNoGameSummary() {
        Long gameId = command.startGame("Germany", "France");
        assertNotNull(gameId);
        List<String> gameSummary = command.getSummaryService().getGamesSummary();
        assertTrue(gameSummary.isEmpty());
    }

    @Test
    public void testWhenGamesHaveSameTotalScoreThenAddedToSummaryOrderedInMostRecentlyAddedFashion(){
        Long game1 = command.startGame("Mexico", "Canada");
        Long game2 = command.startGame("Spain", "Brazil");
        Long game3 = command.startGame("Germany", "France");
        Long game4 = command.startGame("Uruguay", "Italy");
        Long game5 = command.startGame("Argentina", "Australia");

        command.updateGameScore(game1, 0, 5);
        command.updateGameScore(game2, 10, 2);
        command.updateGameScore(game3, 2,2);
        command.updateGameScore(game4, 6,6);
        command.updateGameScore(game5, 3,1);

        command.endGame(game5);
        command.endGame(game3);
        command.endGame(game1);
        command.endGame(game4);
        command.endGame(game2);

        List<String> gamesSummary = command.getSummaryService().getGamesSummary();
        assertEquals("Uruguay 6 - Italy 6", gamesSummary.get(0));
        assertEquals("Spain 10 - Brazil 2", gamesSummary.get(1));
        assertEquals("Mexico 0 - Canada 5", gamesSummary.get(2));
        assertEquals("Argentina 3 - Australia 1", gamesSummary.get(3));
        assertEquals("Germany 2 - France 2", gamesSummary.get(4));
    }

    @Test(expected = IllegalStateException.class)
    public void testWhenGameFinishedThenScoreUpdateThrowsException() {
        Long gameId = command.startGame("Germany", "France");
        assertNotNull(gameId);
        command.endGame(gameId);
        command.updateGameScore(gameId, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenEitherTeamNullThenStartGameFails(){
        command.startGame("Germany", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenGameIdNullThenGetLiveScoreThrowsException(){
        command.getScoreboardService().getLiveScore(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenGameIdNullThenUpdateScoreThrowsException(){
        command.updateGameScore(null, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenScoreNegativeThenUpdateScoreThrowsException(){
        command.updateGameScore(null, -1, 1);
    }

    @Test(expected = IllegalStateException.class)
    public void testWhenGameIdUnknownThenUpdateScoreThrowsException(){
        command.updateGameScore(Long.valueOf(123), 1, 1);
    }

    @Test(expected = IllegalStateException.class)
    public void testWhenGameIdUnknownThenGetLiveScoreThrowsException(){
        command.getScoreboardService().getLiveScore(Long.valueOf(123));
    }
}
