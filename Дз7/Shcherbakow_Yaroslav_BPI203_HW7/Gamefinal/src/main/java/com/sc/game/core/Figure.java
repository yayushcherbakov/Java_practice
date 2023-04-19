package com.sc.game.core;

import static com.sc.game.core.Constants.FIGURE_BOARD_SIZE;

public class Figure {
    private final Board board;

    /**
     * Базовый конструктор Figure.
     */
    public Figure() {
        this.board = new Board(FIGURE_BOARD_SIZE, FIGURE_BOARD_SIZE, CellType.HIDDEN);
    }

    /**
     * Поворот фигуры на 90 градусов вправо.
     *
     * @param figure Поворачиваемая фигура.
     * @return Перевёрнутая фигура.
     */
    public static Figure rightRotation(Figure figure) {
        var newFigure = new Figure();

        //determines the transpose of the matrix
        for (int i = 0; i < FIGURE_BOARD_SIZE; i++) {
            for (int j = 0; j < FIGURE_BOARD_SIZE; j++) {
                newFigure.board.getGrid().get(i).get(j).setType(figure.board.getGrid().get(j).get(i).getType());
            }
        }

        //then we reverse the elements of each row
        for (int i = 0; i < FIGURE_BOARD_SIZE; i++) {
            int low = 0, high = FIGURE_BOARD_SIZE - 1;
            while (low < high) {
                var temp = newFigure.board.getGrid().get(i).get(low).getType();
                newFigure.board.getGrid().get(i).get(low).setType(newFigure.board.getGrid().get(i).get(high).getType());
                newFigure.board.getGrid().get(i).get(high).setType(temp);
                ++low;
                --high;
            }
        }

        return newFigure;
    }

    public Board getBoard() {
        return board;
    }
}
