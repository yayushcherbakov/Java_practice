package com.sc.game;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.TimerTask;

public class TimerHandler extends TimerTask {
    int seconds = 0;
    Label timeSpentLabel;
    int nTicks = 0;

    public TimerHandler(Label timeSpentLabel) {
        this.timeSpentLabel = timeSpentLabel;
    }

    @Override
    public void run() {
        Platform.runLater(() -> timeSpentLabel.setText("Spent time: " + ++seconds));
    }
}
