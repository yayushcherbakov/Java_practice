package game.core;

import java.util.ArrayList;

import static game.core.Constants.FIGURE_BOARD_SIZE;
import static game.core.Constants.GAME_BOARD_SIZE;

public class GameService {

    private Board board = null;

    public Board getBoard() {
        return board;
    }

    private int stepCount = 0;

    public int getStepCounts() {
        return stepCount;
    }

    private Figure currentFigure = null;

    public Figure getCurrentFigure() {
        return currentFigure;
    }

    public GameService() {
    }

    public void restart() {
        this.board = new Board(GAME_BOARD_SIZE, GAME_BOARD_SIZE);
        this.currentFigure = FigureGenerator.GetRandomFigure();
        this.stepCount = 0;
    }

    // Возвращает можно ли сделать шаг
    public boolean makeStep(Position figureCenter, Position toInsert) {

        var absolutePosition = new Position(toInsert.getX() - figureCenter.getX(), toInsert.getY() - figureCenter.getY());

        ArrayList<Position> positionToCheck = new ArrayList<>();

        for (int i = 0; i < FIGURE_BOARD_SIZE; ++i) {
            for (int j = 0; j < FIGURE_BOARD_SIZE; ++j) {
                if (this.currentFigure.getBoard().getGrid().get(i).get(j).GetType() == CellType.FILLED) {
                    positionToCheck.add(new Position(absolutePosition.getX() + i, absolutePosition.getY() + j));
                }
            }
        }
        for (var position : positionToCheck) {
            if (position.getX() < 0 || position.getX() >= GAME_BOARD_SIZE || position.getY() < 0 || position.getY() >= GAME_BOARD_SIZE || this.board.getGrid().get(position.getX()).get(position.getY()).GetType() == CellType.FILLED) {
                return false;
            }
        }

        for (var position : positionToCheck) {
            this.board.getGrid().get(position.getX()).get(position.getY()).setType(CellType.FILLED);
            ;
        }
        ++this.stepCount;
        this.currentFigure = FigureGenerator.GetRandomFigure();
        return true;
    }
}
