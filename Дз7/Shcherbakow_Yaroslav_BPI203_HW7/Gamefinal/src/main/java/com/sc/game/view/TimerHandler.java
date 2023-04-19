package com.sc.game.view;

import javafx.application.Platform;

import java.util.TimerTask;

/**
 * Отображение времени игры.
 */
public class TimerHandler extends TimerTask {
    private int seconds = 0;
    private final TimerListener timerListener;

    /**
     * Обработчик таймера.
     *
     * @param timerListener Слушатель времени.
     */
    public TimerHandler(TimerListener timerListener) {
        this.timerListener = timerListener;
    }

    /**
     * Каждую секунду выводит истекщие секунды игры на экран.
     */
    @Override
    public void run() {
        Platform.runLater(() -> timerListener.onTimerTick(++seconds));
    }
}
