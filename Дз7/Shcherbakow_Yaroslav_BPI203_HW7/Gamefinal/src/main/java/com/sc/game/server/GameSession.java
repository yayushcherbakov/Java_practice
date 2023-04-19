package com.sc.game.server;

import com.sc.game.core.FigureGenerator;
import com.sc.game.transfer.GameResultScoreModelItem;
import com.sc.game.transfer.GameResultType;

import java.util.Collections;
import java.util.Vector;

public class GameSession {
    private final int userNumber;
    private final Vector<Integer> figuresSequence;
    private final Vector<UserScore> scores;

    public GameSession(int userNumber) {
        this.userNumber = userNumber;
        figuresSequence = new Vector<>();
        scores = new Vector<>(userNumber);
    }

    private int getNextFigureIndex() {
        var figureIndex = FigureGenerator.getRandomFigureIndex();
        figuresSequence.add(figureIndex);
        return figureIndex;
    }

    public int getFigureIndexByStep(int stepNumber) {
        synchronized (figuresSequence) {
            if (figuresSequence.size() > stepNumber) {
                return figuresSequence.get(stepNumber);
            }
            return getNextFigureIndex();
        }
    }

    public void setNewScore(int clientId, String name, int stepNumber, long stepTime) {
        scores.add(new UserScore(clientId, name, stepNumber, stepTime));
        isGameEnded();
    }

    public int getCountOfFinishedUsers() {
        return scores.size();
    }

    public boolean isGameEnded() {
        return userNumber == scores.size();
    }

    public void processGameResults() {
        if (userNumber == 1) {
            scores.firstElement().setResult(GameResultType.WON);
            return;
        }
        scores.sort(Collections.reverseOrder());

        var firstScore = scores.firstElement();
        var lastScore = scores.lastElement();
        if (firstScore.compareTo(lastScore) == 0) {
            for (var score : scores) {
                score.setResult(GameResultType.DRAW);
            }
            return;
        }

        for (var score : scores) {
            score.setResult(firstScore.compareTo(score) == 0 ? GameResultType.WON : GameResultType.Lose);
        }
    }

    public Vector<GameResultScoreModelItem> getScores() {
        var result = new Vector<GameResultScoreModelItem>();
        for (var score : scores) {
            var item = new GameResultScoreModelItem();
            item.setClientId();
            item.setName(score.getName());
            item.setStepNumber(score.getStepNumber());
            item.setStepTime(score.getStepTime());
            item.setResult(score.getResult());
            result.add(item);
        }
        return result;
    }

    public GameResultType getResultByUser(int clientId) {
        for (var score : scores) {
            if (score.getClientId() == clientId) {
                return score.getResult();
            }
        }
        return GameResultType.Lose;
    }
}
