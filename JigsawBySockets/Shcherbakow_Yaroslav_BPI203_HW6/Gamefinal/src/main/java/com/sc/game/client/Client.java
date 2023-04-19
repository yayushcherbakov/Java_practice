package com.sc.game.client;

import com.sc.game.core.Board;
import com.sc.game.core.Figure;
import com.sc.game.core.GameService;
import com.sc.game.core.Position;
import com.sc.game.transfer.ActionSender;
import com.sc.game.transfer.ActionType;
import com.sc.game.transfer.EndGameModel;
import com.sc.game.transfer.TransferObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Класс клиента игры.
 */
public class Client {
    private final GameService gameService;
    private ActionListener actionListener;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;

    private ReceiverHandler receiverHandler;

    /**
     * Конструктор клиента без параметров.
     */
    public Client() {
        gameService = new GameService();
    }

    /**
     * Свойство для получения сокета.
     *
     * @return Сокет.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     */
    public ActionListener getActionListener() {
        return actionListener;
    }

    /**
     */
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    /**
     * Подключение к указанному хосту по указанному порту.
     *
     * @param host Хост для подключения.
     * @param port Порт для подключения.
     */
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

    /**
     * Регистрирует имя игрока.
     *
     * @param name Имя для регистрации.
     */
    public void registerName(String name) {
        try {
            var newAction = new TransferObject(ActionType.SET_NAME, name);
            ActionSender.send(objectOutputStream, newAction);
            return;
        } catch (IOException ignored) {
        }
        try {
            // closing resources
            objectOutputStream.close();
            close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Завершение сеанса игры.
     */
    public void finishGame() {
        try {
            var endGameModel = new EndGameModel();
            endGameModel.setStepCount(gameService.getStepCounts());
            endGameModel.setLastStepTime(gameService.getLastStepTime());

            var newAction = new TransferObject(ActionType.END_GAME, endGameModel);
            ActionSender.send(objectOutputStream, newAction);
            return;
        } catch (IOException ignored) {
        }
        try {
            // closing resources
            objectOutputStream.close();
            close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Запрашивает новую игровую сессию.
     */
    public void requestNewGameSession() {
        try {
            var newAction = new TransferObject(ActionType.NEW_GAME);
            ActionSender.send(objectOutputStream, newAction);
            return;
        } catch (IOException ignored) {
        }
        try {
            // closing resources
            objectOutputStream.close();
            close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Извлекает следующую игровую фигуру.
     */
    public void fetchNextFigure() {
        try {
            var newAction = new TransferObject(ActionType.FETCH_NEXT_FIGURE, this.gameService.getStepCounts());
            ActionSender.send(objectOutputStream, newAction);
            return;
        } catch (IOException ignored) {
        }
        try {
            // closing resources
            objectOutputStream.close();
            close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Закрывает приложение клиента.
     */
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
        } catch (IOException ignored) {
        }
    }

    /**
     * Предоставляет игровое поле.
     *
     * @return Игровое поле.
     */
    public Board getBoard() {
        return gameService.getBoard();
    }

    /**
     * Предоставляет текущее количество ходов.
     *
     * @return Текущее количество ходов.
     */
    public int getStepCounts() {
        return gameService.getStepCounts();
    }

    /**
     * Предоставляет текущую игровую фигуру.
     *
     * @return Текущая игровая фигура.
     */
    public Figure getCurrentFigure() {
        return gameService.getCurrentFigure();
    }

    /**
     * Запускает текущую игру.
     *
     * @param firstFigureIndex Индекс первой игровой фигуры.
     */
    public void startGame(int firstFigureIndex) {
        gameService.startGame(firstFigureIndex);
    }

    /**
     * Выполнить ход.
     *
     * @param figureCenter Центр фигуры.
     * @param toInsert     Позиция вставки.
     * @return Совершён ли ход успешно.
     */
    public boolean makeStep(Position figureCenter, Position toInsert) {
        return gameService.makeStep(figureCenter, toInsert);
    }

    /**
     * Присваивает индекс следующей фигуры.
     *
     * @param figureIndex Индекс следующей фигуры.
     */
    public void setNextFigure(int figureIndex) {
        gameService.setNextFigure(figureIndex);
    }
}
