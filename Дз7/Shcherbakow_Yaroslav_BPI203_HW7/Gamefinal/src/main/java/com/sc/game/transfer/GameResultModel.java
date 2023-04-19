package com.sc.game.transfer;

import java.io.Serializable;
import java.util.Vector;

public class GameResultModel implements Serializable {
    private GameResultType userResult;
    private Vector<GameResultScoreModelItem> allResults;

    public GameResultType getUserResult() {
        return userResult;
    }

    public void setUserResult(GameResultType userResult) {
        this.userResult = userResult;
    }

    public Vector<GameResultScoreModelItem> getAllResults() {
        return allResults;
    }

    public void setAllResults(Vector<GameResultScoreModelItem> allResults) {
        this.allResults = allResults;
    }
}
