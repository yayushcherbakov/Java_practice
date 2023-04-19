package com.sc.game.transfer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TopResultsModel implements Serializable {
    private int stepNumber;
    private long stepTime;
    private String login;
    private LocalDateTime finishGameTime;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDateTime getFinishGameTime() {
        return finishGameTime;
    }

    public void setFinishGameTime(LocalDateTime finishGameTime) {
        this.finishGameTime = finishGameTime;
    }
}