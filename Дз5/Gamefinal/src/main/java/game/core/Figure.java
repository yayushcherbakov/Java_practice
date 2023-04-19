package game.core;

import static game.core.Constants.FIGURE_BOARD_SIZE;

public class Figure {
    private Board board = null;

    public Board getBoard() {
        return board;
    }

    public Figure() {
        this.board = new Board(FIGURE_BOARD_SIZE, FIGURE_BOARD_SIZE, CellType.HIDDEN);
    }

    public static Figure rightRotation(Figure figure) {
        var newFigure = new Figure();

        //determines the transpose of the matrix
        for (int i = 0; i < FIGURE_BOARD_SIZE; i++) {
            for (int j = 0; j < FIGURE_BOARD_SIZE; j++) {
                newFigure.board.getGrid().get(i).get(j).setType(figure.board.getGrid().get(j).get(i).GetType());
            }
        }

        //then we reverse the elements of each row
        for (int i = 0; i < FIGURE_BOARD_SIZE; i++) {
            int low = 0, high = FIGURE_BOARD_SIZE - 1;
            while (low < high) {
                var temp = newFigure.board.getGrid().get(i).get(low).GetType();
                newFigure.board.getGrid().get(i).get(low).setType(newFigure.board.getGrid().get(i).get(high).GetType());
                newFigure.board.getGrid().get(i).get(high).setType(temp);
                ++low;
                --high;
            }
        }

        return newFigure;
    }
}
