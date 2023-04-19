package com.sc.game.core;

import java.util.Random;

public class FigureGenerator {
    final private static Random random = new Random();

    /**
     * Возвращает фигуру в виде линии.
     *
     * @return Фигура линия.
     */
    public static Figure createLine() {
        var line = new Figure();
        line.getBoard().getGrid().get(1).get(0).setType(CellType.FILLED);
        line.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        line.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        return line;
    }

    /**
     * Возвращает фигуру в виде точки.
     *
     * @return Фигура в виде точки.
     */
    public static Figure createDot() {
        var dot = new Figure();
        dot.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        return dot;
    }

    /**
     * Вовзращает фигуру в виде малой буквы Т.
     *
     * @return Фигура малая Т.
     */
    public static Figure createSmallT() {
        var smallT = new Figure();
        return getFigure(smallT);
    }

    /**
     * Вовзращает фигуру в виде большой буквы Т.
     *
     * @return Фигура большая Т.
     */
    public static Figure createBigT() {
        var bigT = new Figure();
        bigT.getBoard().getGrid().get(0).get(1).setType(CellType.FILLED);
        return getFigure(bigT);
    }

    private static Figure getFigure(Figure bigT) {
        bigT.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        bigT.getBoard().getGrid().get(2).get(0).setType(CellType.FILLED);
        bigT.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        bigT.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        return bigT;
    }

    /**
     * Вовзращает фигуру в виде малого угла.
     *
     * @return Фигура малый угол.
     */
    public static Figure createSmallAngle() {
        var smallAngle = new Figure();
        smallAngle.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        smallAngle.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        smallAngle.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        return smallAngle;
    }

    /**
     * Вовзращает фигуру в виде большого угла.
     *
     * @return Фигура большой угол.
     */
    public static Figure createBigAngle() {
        var bigAngle = new Figure();
        bigAngle.getBoard().getGrid().get(0).get(2).setType(CellType.FILLED);
        bigAngle.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        bigAngle.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        bigAngle.getBoard().getGrid().get(2).get(0).setType(CellType.FILLED);
        bigAngle.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        return bigAngle;
    }

    /**
     * Вовзращает фигуру в виде правого зигзага угла.
     *
     * @return Фигура правый зигзаг.
     */
    public static Figure createRightZigzag() {
        var rightZigzag = new Figure();
        rightZigzag.getBoard().getGrid().get(0).get(2).setType(CellType.FILLED);
        rightZigzag.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        rightZigzag.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        rightZigzag.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        return rightZigzag;
    }

    /**
     * Вовзращает фигуру в виде левого зигзага.
     *
     * @return Фигура левый зигзаг.
     */
    public static Figure createLeftZigzag() {
        var leftZigzag = new Figure();
        leftZigzag.getBoard().getGrid().get(0).get(0).setType(CellType.FILLED);
        leftZigzag.getBoard().getGrid().get(1).get(0).setType(CellType.FILLED);
        leftZigzag.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        leftZigzag.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        return leftZigzag;
    }

    /**
     * Вовзращает фигуру в виде левого коня.
     *
     * @return Фигура левый конь.
     */
    public static Figure createLeftHorse() {
        Figure leftHorse = new Figure();
        leftHorse.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        leftHorse.getBoard().getGrid().get(2).get(0).setType(CellType.FILLED);
        leftHorse.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        leftHorse.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        return leftHorse;
    }

    /**
     * Вовзращает фигуру в виде правого коня.
     *
     * @return Фигура правый конь.
     */
    public static Figure createRightHorse() {
        var rightHorse = new Figure();
        rightHorse.getBoard().getGrid().get(1).get(0).setType(CellType.FILLED);
        rightHorse.getBoard().getGrid().get(2).get(0).setType(CellType.FILLED);
        rightHorse.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        rightHorse.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        return rightHorse;
    }

    /**
     * Генерирует случайную фигуру.
     *
     * @return Случайно сгенерированная фигура.
     */
    public static Figure getRandomFigure() {
        int type = getRandomFigureIndex();
        return getFigureByIndex(type);
    }

    public static Figure getFigureByIndex(int index){
        return switch (index) {
            case 0 -> createRightHorse();
            case 1 -> Figure.rightRotation(createRightHorse());
            case 2 -> Figure.rightRotation(Figure.rightRotation(createRightHorse()));
            case 3 -> Figure.rightRotation(Figure.rightRotation(Figure.rightRotation(createRightHorse())));
            case 4 -> createLeftHorse();
            case 5 -> Figure.rightRotation(createLeftHorse());
            case 6 -> Figure.rightRotation(Figure.rightRotation(createLeftHorse()));
            case 7 -> Figure.rightRotation(Figure.rightRotation(Figure.rightRotation(createLeftHorse())));
            case 8 -> createLeftZigzag();
            case 9 -> Figure.rightRotation(createLeftZigzag());
            case 10 -> createRightZigzag();
            case 11 -> Figure.rightRotation(createRightZigzag());
            case 12 -> createBigAngle();
            case 13 -> Figure.rightRotation(createBigAngle());
            case 14 -> Figure.rightRotation(Figure.rightRotation(createBigAngle()));
            case 15 -> Figure.rightRotation(Figure.rightRotation(Figure.rightRotation(createBigAngle())));
            case 16 -> createBigT();
            case 17 -> Figure.rightRotation(createBigT());
            case 18 -> Figure.rightRotation(Figure.rightRotation(createBigT()));
            case 19 -> Figure.rightRotation(Figure.rightRotation(Figure.rightRotation(createBigT())));
            case 20 -> createSmallT();
            case 21 -> Figure.rightRotation(createSmallT());
            case 22 -> Figure.rightRotation(Figure.rightRotation(createSmallT()));
            case 23 -> Figure.rightRotation(Figure.rightRotation(Figure.rightRotation(createSmallT())));
            case 24 -> createSmallAngle();
            case 25 -> Figure.rightRotation(createSmallAngle());
            case 26 -> Figure.rightRotation(Figure.rightRotation(createSmallAngle()));
            case 27 -> Figure.rightRotation(Figure.rightRotation(Figure.rightRotation(createSmallAngle())));
            case 28 -> createLine();
            case 29 -> Figure.rightRotation(createLine());
            default -> createDot();
        };
    }

    public static int getRandomFigureIndex() {
        return random.nextInt(Constants.GAME_FIGURE_COUNT);
    }
}
