package com.sc.game.client;

import com.sc.game.transfer.GameResultModel;
import com.sc.game.transfer.StartGameModel;

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
}
