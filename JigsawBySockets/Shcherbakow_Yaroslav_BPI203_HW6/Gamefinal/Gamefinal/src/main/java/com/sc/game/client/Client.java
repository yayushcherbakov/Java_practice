package com.sc.game.client;

import com.sc.game.core.*;
import com.sc.game.transfer.ActionSender;
import com.sc.game.transfer.ActionType;
import com.sc.game.transfer.EndGameModel;
import com.sc.game.transfer.TransferObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private GameService gameService;
    private ActionListener actionListener;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;

    private ReceiverHandler receiverHandler;

    public Client() {
        gameService = new GameService();
    }

    public Socket getSocket() {
        return socket;
    }
    public ActionListener getActionListener() {
        return actionListener;
    }
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void connect(String host, String port) {
        try {
            var parsedPort = Integer.parseInt(port);
            InetAddress ip = InetAddress.getByName(host);

            socket = new Socket(ip, parsedPort);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            receiverHandler = new ReceiverHandler(this);
            receiverHandler.start();
        } catch (IOException | NumberFormatException ex) {
            actionListener.onFailedConnection();
        }
    }

    public void registerName(String name) {
        try {
            var newAction = new TransferObject(ActionType.SET_NAME, name);
            ActionSender.send(objectOutputStream, newAction);
            return;
        } catch (IOException e) {
        }
        try {
            // closing resources
            objectOutputStream.close();
            close();
        } catch (IOException e) {
        }
    }

    public void finishGame() {
        try {
            var endGameModel = new EndGameModel();
            endGameModel.setStepCount(gameService.getStepCounts());
            endGameModel.setLastStepTime(gameService.getLastStepTime());

            var newAction = new TransferObject(ActionType.END_GAME, endGameModel);
            ActionSender.send(objectOutputStream, newAction);
            return;
        } catch (IOException e) {
        }
        try {
            // closing resources
            objectOutputStream.close();
            close();
        } catch (IOException e) {
        }
    }

    public void requestNewGameSession() {
        try {
            var newAction = new TransferObject(ActionType.NEW_GAME);
            ActionSender.send(objectOutputStream, newAction);
            return;
        } catch (IOException e) {
        }
        try {
            // closing resources
            objectOutputStream.close();
            close();
        } catch (IOException e) {
        }
    }

    public void fetchNextFigure(){
        try {
            var newAction = new TransferObject(ActionType.FETCH_NEXT_FIGURE, this.gameService.getStepCounts());
            ActionSender.send(objectOutputStream, newAction);
            return;
        } catch (IOException e) {
        }
        try {
            // closing resources
            objectOutputStream.close();
            close();
        } catch (IOException e) {
        }
    }

    public void close() {
        if (receiverHandler != null) {
            receiverHandler.interrupt();
            receiverHandler = null;
        }

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            actionListener.onLostConnection();
        } catch (IOException e) {
        }
    }

    public Board getBoard() {
        return gameService.getBoard();
    }

    public int getStepCounts() {
        return gameService.getStepCounts();
    }

    public Figure getCurrentFigure() {
        return gameService.getCurrentFigure();
    }

    public void startGame(int firstFigureIndex) {
        gameService.startGame(firstFigureIndex);
    }

    public boolean makeStep(Position figureCenter, Position toInsert) {
        return gameService.makeStep(figureCenter, toInsert);
    }

    public void setNextFigure(int figureIndex){
        gameService.setNextFigure(figureIndex);
    }
}
