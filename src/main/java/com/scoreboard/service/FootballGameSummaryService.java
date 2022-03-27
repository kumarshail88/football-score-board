package com.scoreboard.service;

import com.scoreboard.business.model.GameSummary;
import com.scoreboard.service.api.GameSummaryService;
import com.scoreboard.utils.GameSummaryComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.scoreboard.validation.Validator.validateGameSummary;

public class FootballGameSummaryService implements GameSummaryService {

    private GameSummaryComparator gameSummaryComparator;
    private List<GameSummary> gameSummaryList;

    public FootballGameSummaryService() {
        this.gameSummaryList = new ArrayList<>();
        gameSummaryComparator = new GameSummaryComparator();
    }

    public void addFinishedGame(GameSummary gameSummary) {
        validateGameSummary(gameSummary);
        this.gameSummaryList.add(gameSummary);
    }

    public List<String> getGamesSummary() {
        Collections.sort(gameSummaryList, gameSummaryComparator);
        return gameSummaryList.stream()
                .map(game -> game.getScoreSummary())
                .collect(Collectors.toList());
    }
}
