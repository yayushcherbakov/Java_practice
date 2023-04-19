package com.sc.game.repository;

import java.time.LocalDateTime;

/**
 * Класс модели ответа.
 */
public class ResultResponseModel {
    private int stepNumber;
    private long stepTime;
    private String login;
    private LocalDateTime finishGameTime;

    /**
     * Возвращет количество совершенных ходов.
     */
    public int getStepNumber() {
        return stepNumber;
    }

    /**
     * Устанавливает количество совершенных ходов.
     */
    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    /**
     * Возвращает длительность игры.
     */
    public long getStepTime() {
        return stepTime;
    }

    /**
     * Устанавливает длительность игры.
     */
    public void setStepTime(long stepTime) {
        this.stepTime = stepTime;
    }

    /**
     * Возвращает логин игрока.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Устанавливает логин игрока.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Возвращает время завершения игры.
     */
    public LocalDateTime getFinishGameTime() {
        return finishGameTime;
    }

    /**
     * Устанавливает время завершения игры.
     */
    public void setFinishGameTime(LocalDateTime finishGameTime) {
        this.finishGameTime = finishGameTime;
    }
}
