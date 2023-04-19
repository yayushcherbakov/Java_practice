package com.sc.game.client;

import com.sc.game.transfer.GameResultModel;
import com.sc.game.transfer.StartGameModel;
import com.sc.game.transfer.TopResultsModel;
import com.sc.game.transfer.TransferObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

public class ReceiverHandler extends Thread{
    final Client client;
    final ObjectInputStream objectInputStream;

    public ReceiverHandler(Client client) throws IOException {
        this.client = client;
        this.objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                var receivedObject = (TransferObject)objectInputStream.readObject();
                switch (receivedObject.getActionType()) {
                    case GET_NAME -> client.getActionListener().onGetName();
                    case START_GAME -> {
                        var startInfo = (StartGameModel) receivedObject.getData();
                        client.getActionListener().onGameStart(startInfo);
                    }
                    case WAITING_FOR_OTHER_PLAYERS -> client.getActionListener().onWaitingForOtherPlayers();
                    case CONNECTION_CANCELED -> {
                        client.getActionListener().onConnectionCanceled();
                        this.interrupt();
                    }
                    case PUSH_NEXT_FIGURE -> {
                        var nextFigureIndex = (int) receivedObject.getData();
                        client.getActionListener().onNextFigureFetched(nextFigureIndex);
                    }
                    case PUBLISH_RESULT -> {
                        var gameResultModel = (GameResultModel) receivedObject.getData();
                        client.getActionListener().onGameFinished(gameResultModel);
                    }
                    case OPPONENT_CLOSE_CLIENT -> client.getActionListener().onGameInterrupted();
                    case PUSH_TOP_RESULTS ->{
                        var topResults = (Vector<TopResultsModel>) receivedObject.getData();
                        client.getActionListener().onDisplayTopResults(topResults);
                    }
                    default -> {
                    }
                }
            } catch (IOException e) {
                break;
            } catch (ClassNotFoundException ignored) {
            }
        }
        try {
            // closing resources
            objectInputStream.close();
            client.close();
        } catch (IOException ignored) {
        }
    }
}

