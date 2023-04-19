package com.sc.game.client;

import com.sc.game.transfer.GameResultModel;
import com.sc.game.transfer.StartGameModel;
import com.sc.game.transfer.TransferObject;

import java.io.IOException;
import java.io.ObjectInputStream;

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
                    case GET_NAME:
                        client.getActionListener().onGetName();
                        break;
                    case START_GAME:
                        var startInfo = (StartGameModel)receivedObject.getData();
                        client.getActionListener().onGameStart(startInfo);
                        break;
                    case WAITING_FOR_OTHER_PLAYERS:
                        client.getActionListener().onWaitingForOtherPlayers();
                        break;
                    case CONNECTION_CANCELED:
                        client.getActionListener().onConnectionCanceled();
                        this.interrupt();
                        break;
                    case PUSH_NEXT_FIGURE:
                        var nextFigureIndex = (int)receivedObject.getData();
                        client.getActionListener().onNextFigureFetched(nextFigureIndex);
                        break;
                    case PUBLISH_RESULT:
                        var gameResultModel = (GameResultModel)receivedObject.getData();
                        client.getActionListener().onGameFinished(gameResultModel);
                        break;
                    case OPPONENT_CLOSE_CLIENT:
                        client.getActionListener().onGameInterrupted();
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                break;
            } catch (ClassNotFoundException e) {
            }
        }
        try {
            // closing resources
            objectInputStream.close();
            client.close();
        } catch (IOException e) {
        }
    }
}

