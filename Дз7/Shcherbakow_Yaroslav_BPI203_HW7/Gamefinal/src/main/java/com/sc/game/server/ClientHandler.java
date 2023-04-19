package com.sc.game.server;

import com.sc.game.transfer.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Vector;

class ClientHandler extends Thread {
    private final ClientInformation clientInformation;
    private final Server server;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private final Socket socket;

    public ClientHandler(Socket socket, int id, Server server) throws IOException {
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.server = server;
        this.socket = socket;
        clientInformation = new ClientInformation(id);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                var receivedObject = (TransferObject) objectInputStream.readObject();
                processRequest(receivedObject);
                System.out.println(this.clientInformation.getId() + " : " + receivedObject.getData());
            } catch (IOException e) {
                break;
            } catch (ClassNotFoundException ignored) {
            }
        }
        try {
            // closing resources
            close();
            System.out.println("Client " + this.clientInformation.getId() + " closed");

            interruptGameSessionIfStarted();

            this.server.getClients().remove(this);
        } catch (IOException ignored) {
        }
    }

    private void processRequest(TransferObject model) throws IOException {
        switch (model.getActionType()) {
            case SET_NAME -> {
                clientInformation.setName((String) model.getData());
                requestNewGameSession();
            }
            case FETCH_NEXT_FIGURE -> {
                var nextFigureIndex = server.getGameSession().getFigureIndexByStep((int) model.getData());
                ActionSender.sendNextFigure(this.objectOutputStream, nextFigureIndex);
            }
            case END_GAME -> processEndGame(model);
            case NEW_GAME -> requestNewGameSession();
            case GET_TOP_RESULTS -> getTopResults();
            default -> {
            }
        }
    }

    private void requestNewGameSession() throws IOException {
        clientInformation.setState(true);
        if (isAllPlayersReady()) {
            server.setGameSession(new GameSession(server.getMaxUsersCount()));
            for (var clientHandler : server.getClients()) {
                var startGameModel = new StartGameModel();
                startGameModel.setMaxGameTime(server.getMaxGameTime());
                startGameModel.setMaxUsersCount();
                startGameModel.setOpponents(getOpponentsNames(clientHandler));
                startGameModel.setFirstFigureIndex(server.getGameSession().getFigureIndexByStep(0));
                ActionSender.sendStartGame(clientHandler.objectOutputStream, startGameModel);
            }
            return;
        }
        ActionSender.sendWaitingForOtherPlayers(objectOutputStream);
    }

    private void processEndGame(TransferObject model) throws IOException {
        var endGameModel = (EndGameModel) model.getData();
        synchronized (server.getGameSession()) {
            server.getGameSession().setNewScore(this.clientInformation.getId(), this.clientInformation.getName(),
                    endGameModel.getStepCount(), endGameModel.getLastStepTime());
            if (server.getGameSession().getCountOfFinishedUsers() == server.getMaxUsersCount()) {
                server.getGameSession().processGameResults();
                var allResults = server.getGameSession().getScores();
                var finishTime = LocalDateTime.now(ZoneOffset.UTC);
                for(var result : allResults){
                    server.getRepository().AddResult(finishTime, result.getName(), result.getStepNumber(), result.getStepTime());
                }
                for (var clientHandler : server.getClients()) {
                    var gameResultModel = new GameResultModel();
                    gameResultModel.setAllResults(allResults);
                    gameResultModel.setUserResult(server.getGameSession().getResultByUser(clientHandler.clientInformation.getId()));
                    ActionSender.sendGameResults(clientHandler.objectOutputStream, gameResultModel);
                    clientHandler.clientInformation.setState(false);
                }
            }
        }
    }

    private void interruptGameSessionIfStarted() throws IOException {
        if (isAllPlayersReady()) {
            for (var clientHandler : server.getClients()) {
                if (clientHandler != this) {
                    ActionSender.sendGameInterruption(clientHandler.objectOutputStream);
                    clientHandler.clientInformation.setState(false);
                }
            }
        }
    }

    private boolean isAllPlayersReady() {
        for (var client : server.getClients()) {
            if (!client.clientInformation.getState()) {
                return false;
            }
        }
        return server.getClients().size() == server.getMaxUsersCount();
    }

    private Vector<String> getOpponentsNames(ClientHandler clientHandler) {
        var opponents = new Vector<String>();
        for (var client : server.getClients()) {
            if (client != clientHandler) {
                opponents.add(client.clientInformation.getName());
            }
        }
        return opponents;
    }

    public void cancelConnection() throws IOException {
        ActionSender.sendCancelByMaxCount(objectOutputStream);
        close();
        System.out.println("User connection canceled. Maximum number of users reached.");
    }

    public void requestNameOfUser() throws IOException {
        ActionSender.sendRequestName(objectOutputStream);
    }

    public void getTopResults() throws IOException {
        var topResultsModel = new Vector<TopResultsModel>();
        var results = server.getRepository().GetTopResults();
        for (var result : results) {
            var view = new TopResultsModel();
            view.setLogin(result.getLogin());
            view.setFinishGameTime(result.getFinishGameTime());
            view.setStepNumber(result.getStepNumber());
            view.setStepTime(result.getStepTime());
            topResultsModel.add(view);
        }
        ActionSender.sendTopResults(objectOutputStream, topResultsModel);
    }

    public void close() throws IOException{
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
    }
}
