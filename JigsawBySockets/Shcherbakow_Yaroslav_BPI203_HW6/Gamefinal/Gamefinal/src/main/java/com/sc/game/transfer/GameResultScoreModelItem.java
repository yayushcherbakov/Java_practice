package com.sc.game.transfer;

import java.io.Serializable;

public class GameResultScoreModelItem implements Serializable {
    private int clientId;
    private int stepNumber;
    private long stepTime;
    private String name;
    private GameResultType result;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public long getStepTime() {
        return stepTime;
    }

    public void setStepTime(long stepTime) {
        this.stepTime = stepTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameResultType getResult() {
        return result;
    }

    public void setResult(GameResultType result) {
        this.result = result;
    }
}
