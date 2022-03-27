package com.scoreboard.test;

import com.scoreboard.factory.GamesFactory;
import com.scoreboard.repo.model.Game;
import org.junit.Assert;
import org.junit.Test;

public class GameFactoryTest {

    @Test
    public void testGameDetailsCorrectThenGameCreationSuccessful(){
        Game game = GamesFactory.createNewGame("Germany", "Mexico");
        Assert.assertNotNull(game);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenTeamNamesMissingThenGameCreationThrowsException(){
        Game game = GamesFactory.createNewGame(null, "Mexico");
    }
}
