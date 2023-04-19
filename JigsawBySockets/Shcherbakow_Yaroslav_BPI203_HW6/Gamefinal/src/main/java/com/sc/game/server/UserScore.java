package com.sc.game.server;

import com.sc.game.transfer.GameResultType;

public class UserScore implements Comparable<UserScore> {
    private final int clientId;
    private final int stepNumber;
    private final long stepTime;
    private final String name;
    private GameResultType result;

    public UserScore(int clientId, String name, int stepNumber, long stepTime) {
        this.stepNumber = stepNumber;
        this.stepTime = stepTime;
        this.clientId = clientId;
        this.name = name;
    }

    public int getClientId() {
        return clientId;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public long getStepTime() {
        return stepTime;
    }

    public String getName() {
        return name;
    }

    public GameResultType getResult() {
        return result;
    }

    public void setResult(GameResultType result) {
        this.result = result;
    }

    @Override
    public int compareTo(UserScore object) {
        if (this.stepNumber == object.stepNumber) {
            if (this.stepTime == object.stepTime) {
                return 0;
            }
            return this.stepTime < object.stepTime ? 1 : -1;
        }
        return this.stepNumber > object.stepNumber ? 1 : -1;
    }
}
