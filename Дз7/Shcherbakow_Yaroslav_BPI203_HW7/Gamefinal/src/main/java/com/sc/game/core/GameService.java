package com.sc.game.core;

import java.util.ArrayList;

import static com.sc.game.core.Constants.FIGURE_BOARD_SIZE;
import static com.sc.game.core.Constants.GAME_BOARD_SIZE;

public class GameService {

    private Board board = null;
    private int stepCount = 0;
    private long startTime;
    private long lastStepTime;
    private Figure currentFigure = null;

    public GameService() {
    }

    public Board getBoard() {
        return board;
    }

    public int getStepCounts() {
        return stepCount;
    }
    public long getLastStepTime() {
        return lastStepTime;
    }

    public Figure getCurrentFigure() {
        return currentFigure;
    }

    public void startGame(int firstFigureIndex) {
        this.board = new Board(GAME_BOARD_SIZE, GAME_BOARD_SIZE);
        this.currentFigure = FigureGenerator.getFigureByIndex(firstFigureIndex);
        this.stepCount = 0;
        this.startTime = System.currentTimeMillis();
        this.lastStepTime = 0;
    }

    // Возвращает можно ли сделать шаг
    public boolean makeStep(Position figureCenter, Position toInsert) {

        var absolutePosition = new Position(toInsert.getX() - figureCenter.getX(), toInsert.getY() - figureCenter.getY());

        ArrayList<Position> positionToCheck = new ArrayList<>();

        for (int i = 0; i < FIGURE_BOARD_SIZE; ++i) {
            for (int j = 0; j < FIGURE_BOARD_SIZE; ++j) {
                if (this.currentFigure.getBoard().getGrid().get(i).get(j).getType() == CellType.FILLED) {
                    positionToCheck.add(new Position(absolutePosition.getX() + i, absolutePosition.getY() + j));
                }
            }
        }
        for (var position : positionToCheck) {
            if (position.getX() < 0 || position.getX() >= GAME_BOARD_SIZE || position.getY() < 0 || position.getY() >= GAME_BOARD_SIZE || this.board.getGrid().get(position.getX()).get(position.getY()).getType() == CellType.FILLED) {
                return false;
            }
        }

        for (var position : positionToCheck) {
            this.board.getGrid().get(position.getX()).get(position.getY()).setType(CellType.FILLED);
        }
        ++this.stepCount;
        this.lastStepTime = System.currentTimeMillis() - startTime;
        return true;
    }

    public void setNextFigure(int figureIndex){
        this.currentFigure = FigureGenerator.getFigureByIndex(figureIndex);
    }
}
