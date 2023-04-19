package com.sc.game.transfer;

import java.io.Serializable;
import java.util.Vector;

public class StartGameModel implements Serializable {
    private int maxGameTime;
    private Vector<String> opponents;
    private int firstFigureIndex;

    public void setMaxUsersCount() {
    }

    public int getMaxGameTime() {
        return maxGameTime;
    }

    public void setMaxGameTime(int maxGameTime) {
        this.maxGameTime = maxGameTime;
    }

    public Vector<String> getOpponents() {
        return opponents;
    }

    public void setOpponents(Vector<String> opponents) {
        this.opponents = opponents;
    }

    public int getFirstFigureIndex() {
        return firstFigureIndex;
    }

    public void setFirstFigureIndex(int firstFigureIndex) {
        this.firstFigureIndex = firstFigureIndex;
    }
}
