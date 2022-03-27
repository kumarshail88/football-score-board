package com.scoreboard.repo.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private Long gameId;
    private Team homeTeam;
    private Team guestTeam;
    private GameStatus status;
    private Map<String, AtomicInteger> score;

    public Game(Long gameId, Team homeTeam, Team guestTeam){
        this.gameId = gameId;
        this.homeTeam = homeTeam;
        this.guestTeam = guestTeam;
        this.status = GameStatus.STARTED;

        score = new HashMap<>();
        score.put(homeTeam.getName(), new AtomicInteger(0));
        score.put(guestTeam.getName(), new AtomicInteger(0));
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getGuestTeam() {
        return guestTeam;
    }

    public Long getGameId() {
        return gameId;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getScore(){
        return score.get(homeTeam.getName()) +  " - " + score.get(guestTeam.getName());
    }

    public String getScoreSummary(){
        return homeTeam.getName()
                + " "
                + score.get(homeTeam.getName())
                +  " - "
                + guestTeam.getName()
                + " "
                + score.get(guestTeam.getName());
    }

    public Integer getTotalScore(){
        return score.get(homeTeam.getName()).get() + score.get(guestTeam.getName()).get();
    }

    public void updateScore(int homeTeamScoreToAdd, int guestTeamScoreToAdd){
        updateHomeTeamScore(homeTeamScoreToAdd);
        updateGuesTeamScore(guestTeamScoreToAdd);
    }

    private void updateHomeTeamScore(int scoreToAdd){
        score.get(this.getHomeTeam().getName()).addAndGet(scoreToAdd);
    }

    private void updateGuesTeamScore(int scoreToAdd){
        score.get(this.getGuestTeam().getName()).addAndGet(scoreToAdd);
    }

    @Override
    public String toString() {
        return gameId + " " + homeTeam.toString() + " - " + guestTeam.toString();
    }
}
