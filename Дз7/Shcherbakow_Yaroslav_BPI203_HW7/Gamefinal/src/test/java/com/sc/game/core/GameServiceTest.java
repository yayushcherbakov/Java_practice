package com.sc.game.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    @Test
    void getBoard() {
//        int boardSize = 0;
//        GameService gameService = new GameService();
//        Assertions.assertNotNull(gameService);
//        Assertions.assertEquals(boardSize, gameService.getBoard().getGrid().size());
    }

    @Test
    void getStepCounts() {
        int stepCount = 0;
        GameService gameService = new GameService();
        Assertions.assertNotNull(gameService);
        Assertions.assertEquals(stepCount, gameService.getStepCounts());
    }

    @Test
    void getCurrentFigure() {
        GameService gameService = new GameService();
        gameService.startGame(0);
        Figure figure = gameService.getCurrentFigure();
        Assertions.assertNotNull(figure);
    }

    @Test
    void startGame() {
        GameService gameService = new GameService();
        gameService.startGame(0);
        int gridSize = 9, stepCount = 0;
        Assertions.assertNotNull(gameService);
        Assertions.assertEquals(gridSize, gameService.getBoard().getGrid().size());
        Assertions.assertEquals(stepCount, gameService.getStepCounts());
        Figure figure = gameService.getCurrentFigure();
        Assertions.assertNotNull(figure);
    }

    @Test
    void makeStep() {
    }
}