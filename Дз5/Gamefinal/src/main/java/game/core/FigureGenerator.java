package game.core;

import java.util.Random;

public class FigureGenerator {
    private static Random random = new Random();

    public static Figure createLine() {
        var line = new Figure();
        line.getBoard().getGrid().get(1).get(0).setType(CellType.FILLED);
        line.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        line.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        return line;
    }

    public static Figure createDot() {
        var dot = new Figure();
        dot.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        return dot;
    }

    public static Figure createSmallT() {
        var smallT = new Figure();
        smallT.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        smallT.getBoard().getGrid().get(2).get(0).setType(CellType.FILLED);
        smallT.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        smallT.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        return smallT;
    }

    public static Figure createBigT() {
        var bigT = new Figure();
        bigT.getBoard().getGrid().get(0).get(1).setType(CellType.FILLED);
        bigT.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        bigT.getBoard().getGrid().get(2).get(0).setType(CellType.FILLED);
        bigT.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        bigT.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        return bigT;
    }

    public static Figure createSmallAngle() {
        var smallAngle = new Figure();
        smallAngle.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        smallAngle.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        smallAngle.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        return smallAngle;
    }

    public static Figure createBigAngle() {
        var bigAngle = new Figure();
        bigAngle.getBoard().getGrid().get(0).get(2).setType(CellType.FILLED);
        bigAngle.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        bigAngle.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        bigAngle.getBoard().getGrid().get(2).get(0).setType(CellType.FILLED);
        bigAngle.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        return bigAngle;
    }

    public static Figure createRightZigzag() {
        var rightZigzag = new Figure();
        rightZigzag.getBoard().getGrid().get(0).get(2).setType(CellType.FILLED);
        rightZigzag.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        rightZigzag.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        rightZigzag.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        return rightZigzag;
    }

    public static Figure createLeftZigzag() {
        var leftZigzag = new Figure();
        leftZigzag.getBoard().getGrid().get(0).get(0).setType(CellType.FILLED);
        leftZigzag.getBoard().getGrid().get(1).get(0).setType(CellType.FILLED);
        leftZigzag.getBoard().getGrid().get(1).get(1).setType(CellType.FILLED);
        leftZigzag.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        return leftZigzag;
    }

    public static Figure createLeftHorse() {
        Figure leftHorse = new Figure();
        leftHorse.getBoard().getGrid().get(1).get(2).setType(CellType.FILLED);
        leftHorse.getBoard().getGrid().get(2).get(0).setType(CellType.FILLED);
        leftHorse.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        leftHorse.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        return leftHorse;
    }

    public static Figure createRightHorse() {
        var rightHorse = new Figure();
        rightHorse.getBoard().getGrid().get(1).get(0).setType(CellType.FILLED);
        rightHorse.getBoard().getGrid().get(2).get(0).setType(CellType.FILLED);
        rightHorse.getBoard().getGrid().get(2).get(1).setType(CellType.FILLED);
        rightHorse.getBoard().getGrid().get(2).get(2).setType(CellType.FILLED);
        return rightHorse;
    }

    public static Figure GetRandomFigure() {
        int type = random.nextInt(31);
        return switch (type) {
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
}
