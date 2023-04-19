package com.sc.game.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FigureTest {

    @Test
    void getBoard() {
        int gridSize = 3;
        Figure figure = new Figure();
        Assertions.assertEquals(gridSize, figure.getBoard().getGrid().size());
    }

    @Test
    void rightRotation() {
        Figure figure = FigureGenerator.createLine();
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(0).getType());

        figure = Figure.rightRotation(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(0).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(1).getType());
    }
}