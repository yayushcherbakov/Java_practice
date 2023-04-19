package com.sc.game.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FigureGeneratorTest {

    @Test
    void createLine() {
        Figure figure = FigureGenerator.createLine();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(2).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void createDot() {
        Figure figure = FigureGenerator.createDot();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(1).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void createSmallT() {
        Figure figure = FigureGenerator.createSmallT();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(2).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void createBigT() {
        Figure figure = FigureGenerator.createBigT();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(0).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(2).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void createSmallAngle() {
        Figure figure = FigureGenerator.createSmallAngle();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(2).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(2).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void createBigAngle() {
        Figure figure = FigureGenerator.createBigAngle();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(0).get(2).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(2).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(2).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(1).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void createRightZigzag() {
        Figure figure = FigureGenerator.createRightZigzag();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(0).get(2).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(2).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(1).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void createLeftZigzag() {
        Figure figure = FigureGenerator.createLeftZigzag();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(0).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(1).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void createLeftHorse() {
        Figure figure = FigureGenerator.createLeftHorse();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(2).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(2).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void createRightHorse() {
        Figure figure = FigureGenerator.createRightHorse();
        Assertions.assertNotNull(figure);
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(1).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(0).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(1).getType());
        Assertions.assertEquals(CellType.FILLED, figure.getBoard().getGrid().get(2).get(2).getType());
        checkNotFilledFields(figure);
    }

    @Test
    void getRandomFigure() {
        Figure figure = FigureGenerator.getRandomFigure();
        Assertions.assertNotNull(figure);
    }

    private void checkNotFilledFields(Figure figure) {
        for (var array : figure.getBoard().getGrid()) {
            for (var cell : array) {
                if (cell.getType() != CellType.FILLED) {
                    Assertions.assertEquals(CellType.HIDDEN, cell.getType());
                }
            }
        }
    }
}