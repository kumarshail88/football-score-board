package com.scoreboard.service.api;

import java.util.List;

public interface GameSummaryService {
    void addFinishedGame(com.scoreboard.business.model.GameSummary gameSummary);
    List<String> getGamesSummary();
}
