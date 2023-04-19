package com.sc.game.transfer;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ActionSender {

    public static void send(ObjectOutputStream objectOutputStream, TransferObject transferObject) throws IOException {
        objectOutputStream.writeObject(transferObject);
        objectOutputStream.flush();
    }

    public static void sendCancelByMaxCount(ObjectOutputStream objectOutputStream) throws IOException {
        var transferObject = new TransferObject(ActionType.CONNECTION_CANCELED);
        send(objectOutputStream, transferObject);
    }

    public static void sendRequestName(ObjectOutputStream objectOutputStream) throws IOException {
        var transferObject = new TransferObject(ActionType.GET_NAME);
        send(objectOutputStream, transferObject);
    }

    public static void sendWaitingForOtherPlayers(ObjectOutputStream objectOutputStream) throws IOException {
        var transferObject = new TransferObject(ActionType.WAITING_FOR_OTHER_PLAYERS);
        send(objectOutputStream, transferObject);
    }

    public static void sendStartGame(ObjectOutputStream objectOutputStream, StartGameModel startGameModel) throws IOException {
        var transferObject = new TransferObject(ActionType.START_GAME, startGameModel);
        send(objectOutputStream, transferObject);
    }

    public static void sendNextFigure(ObjectOutputStream objectOutputStream, int nextFigureIndex) throws IOException {
        var transferObject = new TransferObject(ActionType.PUSH_NEXT_FIGURE, nextFigureIndex);
        send(objectOutputStream, transferObject);
    }

    public static void sendGameResults(ObjectOutputStream objectOutputStream, GameResultModel gameResultModel) throws IOException {
        var transferObject = new TransferObject(ActionType.PUBLISH_RESULT, gameResultModel);
        send(objectOutputStream, transferObject);
    }

    public static void sendGameInterruption(ObjectOutputStream objectOutputStream) throws IOException {
        var transferObject = new TransferObject(ActionType.OPPONENT_CLOSE_CLIENT);
        send(objectOutputStream, transferObject);
    }
}
