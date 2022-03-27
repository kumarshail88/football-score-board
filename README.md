# Football Scoreboard

###Service architecture
This application must be initialized with call to ScoreboardApp.initApp method.
The app consists of three main services: 
####GameService 
This service is the core service that manages the entire lifecycle of the games.  

####ScoreboardService
This service is responsible for maintaining the scoreboard for running games. Every new game is registered 
to the scoreboard by GameService. As soon as the game is finished, the game is de-registered from the scoreboard
and added to games summary.

####GamesSummaryService
Responsible for maintaining the summary of finished games. This service does not know about any of the running games. 

### Assumptions
Game is marked as started as soon as it's displayed on live scoreboard.
Game is added to summary only when finished.

###Tests
Unit and Integration tests have been added to respective business logic.
Some tests might seem to be duplicate, but they ensure the application integrity.