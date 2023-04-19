package com.sc.game.client;

import com.sc.game.transfer.GameResultModel;
import com.sc.game.transfer.StartGameModel;
import com.sc.game.transfer.TopResultsModel;

import java.util.Vector;

public interface ActionListener {
    void onLostConnection();
    void onFailedConnection();
    void onConnectionCanceled();
    void onGetName();
    void onGameStart(StartGameModel startGameModel);
    void onWaitingForOtherPlayers();
    void onNextFigureFetched(int nextFigureIndex);
    void onGameFinished(GameResultModel gameResultModel);
    void onGameInterrupted();
    void onDisplayTopResults(Vector<TopResultsModel> topResultsModel);
}
