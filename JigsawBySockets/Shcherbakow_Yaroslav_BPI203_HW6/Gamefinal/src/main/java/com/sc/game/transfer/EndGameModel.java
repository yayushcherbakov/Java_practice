package com.sc.game.transfer;

import java.io.Serializable;

public class EndGameModel implements Serializable {
    private int stepCount;
    private long lastStepTime;

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public long getLastStepTime() {
        return lastStepTime;
    }

    public void setLastStepTime(long lastStepTime) {
        this.lastStepTime = lastStepTime;
    }
}
