package com.sc.game.view;

import com.sc.game.client.ActionListener;
import com.sc.game.client.Client;
import com.sc.game.core.Board;
import com.sc.game.core.CellType;
import com.sc.game.core.Constants;
import com.sc.game.core.Position;
import com.sc.game.transfer.GameResultModel;
import com.sc.game.transfer.StartGameModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Timer;

public class GameController implements ActionListener, TimerListener {
    /**
     * Игровое поле.
     */
    @FXML
    public GridPane gameGrid;

    /**
     * Поле для генерации игровых фигур.
     */
    @FXML
    public GridPane subGrid;

    /**
     * Лейбл затраченного времени на игру.
     */
    @FXML
    private Label timeSpentLabel;

    /**
     * Лейбл количества ходов.
     */
    @FXML
    private Label stepCountLabel;

    /**
     * Кнопка финиша.
     */
    @FXML
    private Button finishButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button registerButton;

    @FXML
    private Label stateLabel;

    @FXML
    private TextField hostTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private Button connectButton;

    @FXML
    private Label maxGameTimeLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private ListView<String> opponentsListView;

    @FXML
    private Label resultLabel;

    @FXML
    private ListView<String> resultsListView;

    @FXML
    private Button exitButton;

    /**
     * Кнопка перезапуска.
     */
    @FXML
    private Button restartButton;

    /**
     * Координаты.
     */
    private double startX;
    private double startY;

    /**
     * Таймер.
     */
    private Timer timer = null;
    private Client client;
    private ArrayList<Rectangle> gameGridCells = null;
    private ArrayList<Rectangle> subGridCells = null;
    private Position subBoardClickPosition;
    private int maxGameTime = 0;

    /**
     * Нажатие на кнопку перезапуска.
     */
    @FXML
    protected void onRestartButtonClick() {
        client.requestNewGameSession();
    }

    /**
     * Нажатие на кнопку финиша.
     */
    @FXML
    protected void onFinishButtonClick() {
        this.clearSubBoard();
        if (timer == null) {
            return;
        }
        timer.cancel();
        timer = null;

        client.finishGame();
        showStateLabel("Waiting for the other players to finish the game...");
    }

    @FXML
    protected void onRegisterButtonClick() {
        var name = this.nameTextField.getText();
        if (name.isBlank()) {
            return;
        }
        this.userNameLabel.setText("Your name: " + this.nameTextField.getText());
        this.client.registerName(this.nameTextField.getText());
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
        close();
    }

    @FXML
    protected void onConnectButtonClick() {
        this.client.connect(this.hostTextField.getText(), this.portTextField.getText());
    }

    /**
     * Закрытие.
     */
    protected void close() {
        if (timer != null) {
            timer.cancel();
        }
        client.close();
    }

    /**
     * Задает GameService.
     */
    public void setClient() {
        this.client = new Client();;
        client.setActionListener(this);

        this.hostTextField.setText(Constants.DEFAULT_HOST);
        this.portTextField.setText(String.valueOf(Constants.DEFAULT_SERVER_PORT));
    }

    /**
     * Создает Board.
     */
    public ArrayList<Rectangle> createBoard(GridPane grid, Board board) {
        int height = board.getGrid().size();
        if (height < 1) {
            return new ArrayList<>();
        }
        int width = board.getGrid().get(0).size();
        if (width < 1) {
            return new ArrayList<>();
        }

        ArrayList<Rectangle> cells = new ArrayList<>();

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Rectangle rectangle = new Rectangle(45, 45);
                GridPane.setHgrow(rectangle, Priority.ALWAYS);
                GridPane.setVgrow(rectangle, Priority.ALWAYS);
                var color = getColorByCellType(board.getGrid().get(i).get(j).getType(), (i + j / 3 * 3) % 6 > 2);
                rectangle.setFill(color);

                GridPane.setMargin(rectangle, new Insets(1.5));
                grid.add(rectangle, j, i);

                cells.add(rectangle);
            }
        }

        return cells;
    }

    /**
     * Возвращает цвет ячейки.
     */
    private Color getColorByCellType(CellType type, boolean isGreyEmpty) {
        return switch (type) {
            case EMPTY -> isGreyEmpty ? Color.rgb(235, 235, 235) : Color.rgb(255, 255, 255);
            case FILLED -> Color.rgb(34, 64, 140);
            default -> Color.rgb(0, 0, 0, 0);
        };
    }

    /**
     * Обнуляет координаты ноды.
     */
    private void setDefaultPosition(Node node) {
        node.setTranslateX(0);
        node.setTranslateY(0);
    }

    /**
     * Делает ноду Draggable.
     */
    private void makeDraggableNode(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            startX = mouseEvent.getSceneX() - node.getTranslateX();
            startY = mouseEvent.getSceneY() - node.getTranslateY();
            setSubBoardRelativePosition(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        });
        node.setOnMouseDragged(mouseEvent -> {
            node.setTranslateX(mouseEvent.getSceneX() - startX);
            node.setTranslateY(mouseEvent.getSceneY() - startY);
        });
        node.setOnMouseReleased(e -> {
            var gameBoardClickPosition = getGameBoardRelativePosition(e.getSceneX(), e.getSceneY());
            setDefaultPosition(subGrid);
            if (gameBoardClickPosition.getX() != -1 && client.makeStep(subBoardClickPosition, gameBoardClickPosition)) {
                refreshGameBoard();
                clearSubBoard();
                refreshStepsCounter();
                client.fetchNextFigure();
            }
        });
    }

    /**
     * Делает ноду NotDraggable.
     */
    private void makeNotDraggableNode(Node node) {
        node.setOnMousePressed(mouseEvent -> {
        });
        node.setOnMouseDragged(mouseEvent -> {
        });
        node.setOnMouseReleased(e -> {
        });
    }

    private Position getGameBoardRelativePosition(double cursorPositionX, double cursorPositionY) {
        for (Rectangle rectangle : this.gameGridCells) {
            var bound = rectangle.localToScene(rectangle.getBoundsInLocal());
            if (cursorPositionX > bound.getMinX() && cursorPositionX < bound.getMaxX()
                    && cursorPositionY > bound.getMinY() && cursorPositionY < bound.getMaxY()) {
                return new Position(GridPane.getRowIndex(rectangle), GridPane.getColumnIndex(rectangle));
            }
        }
        return new Position(-1, -1);
    }

    private void setSubBoardRelativePosition(double cursorPositionX, double cursorPositionY) {
        for (Rectangle rectangle : this.subGridCells) {
            var bound = rectangle.localToScene(rectangle.getBoundsInLocal());

            if (cursorPositionX > bound.getMinX() && cursorPositionX < bound.getMaxX()
                    && cursorPositionY > bound.getMinY() && cursorPositionY < bound.getMaxY()) {
                subBoardClickPosition = new Position(GridPane.getRowIndex(rectangle), GridPane.getColumnIndex(rectangle));
                break;
            }
        }
    }

    /**
     * Обновляет StepsCounter.
     */
    private void refreshStepsCounter() {
        this.stepCountLabel.setText("Steps count: " + this.client.getStepCounts());
    }

    /**
     * Обновляет GameBoard.
     */
    private void refreshGameBoard() {
        if (gameGridCells != null) {
            gameGrid.getChildren().removeAll(gameGridCells);
        }
        gameGridCells = createBoard(gameGrid, this.client.getBoard());
    }

    /**
     * Обновляет SubBoard.
     */
    private void refreshSubBoard() {
        if (subGridCells != null) {
            subGrid.getChildren().removeAll(subGridCells);
        }
        subGridCells = createBoard(subGrid, this.client.getCurrentFigure().getBoard());
        makeDraggableNode(subGrid);
    }

    private void clearSubBoard() {
        if (subGridCells != null) {
            subGrid.getChildren().removeAll(subGridCells);
        }
        makeNotDraggableNode(subGrid);
    }


    @Override
    public void onLostConnection() {

        Platform.runLater(() -> {
            if (stateLabel.getText() == "Connect canceled. Max number of players reached") {
                return;
            }
            showStateLabel("Lost connection with server");
        });
    }

    @Override
    public void onFailedConnection() {
        Platform.runLater(() -> showStateLabel("Can't connect to server"));
    }

    @Override
    public void onConnectionCanceled() {
        Platform.runLater(() -> showStateLabel("Connect canceled. Max number of players reached"));
    }

    @Override
    public void onGetName() {
        Platform.runLater(() ->
        {
            hideAllControls();

            nameTextField.setVisible(true);
            registerButton.setVisible(true);
        });
    }

    @Override
    public void onGameStart(StartGameModel startGameModel) {
        Platform.runLater(() ->
        {
            hideAllControls();
            showGameControls();

            this.opponentsListView.getItems().clear();
            for (var opponent : startGameModel.getOpponents()) {
                this.opponentsListView.getItems().add(opponent);
            }
            this.maxGameTime = startGameModel.getMaxGameTime();
            this.maxGameTimeLabel.setText("Max game time: " + this.maxGameTime);

            this.client.startGame(startGameModel.getFirstFigureIndex());

            if (gameGridCells != null) {
                gameGrid.getChildren().removeAll(gameGridCells);
            }
            if (subGridCells != null) {
                subGrid.getChildren().removeAll(subGridCells);
            }

            gameGridCells = createBoard(gameGrid, this.client.getBoard());
            subGridCells = createBoard(subGrid, this.client.getCurrentFigure().getBoard());
            makeDraggableNode(subGrid);


            if (timer != null) {
                timer.cancel();
            }
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerHandler(this), 0, 1000);

            refreshStepsCounter();
        });
    }

    @Override
    public void onWaitingForOtherPlayers() {
        Platform.runLater(() -> showStateLabel("Waiting for other players..."));
    }

    @Override
    public void onNextFigureFetched(int nextFigureIndex) {
        Platform.runLater(() ->
        {
            client.setNextFigure(nextFigureIndex);
            refreshSubBoard();
        });
    }

    @Override
    public void onGameFinished(GameResultModel gameResultModel) {
        Platform.runLater(() ->
        {
            hideAllControls();
            showResultControls();

            resultLabel.setText("You result: " + gameResultModel.getUserResult().name());
            this.resultsListView.getItems().clear();
            for (var result : gameResultModel.getAllResults()) {
                this.resultsListView.getItems().add(result.getName() + ": " + result.getResult().name() + " with step count: "
                        + result.getStepNumber() + " in " + result.getStepTime() + "ms");
            }
        });
    }

    @Override
    public void onGameInterrupted() {
        Platform.runLater(() ->
        {
            hideAllControls();
            showResultControls();

            resultLabel.setText("Opponent leave. You WON!");
            this.resultsListView.getItems().clear();
        });
    }

    public void showStateLabel(String text) {
        hideAllControls();

        stateLabel.setVisible(true);
        stateLabel.setText(text);
    }

    private void hideAllControls() {
        gameGrid.setVisible(false);
        subGrid.setVisible(false);
        timeSpentLabel.setVisible(false);
        stepCountLabel.setVisible(false);
        finishButton.setVisible(false);
        opponentsListView.setVisible(false);
        maxGameTimeLabel.setVisible(false);
        userNameLabel.setVisible(false);

        resultsListView.setVisible(false);
        exitButton.setVisible(false);
        resultLabel.setVisible(false);
        restartButton.setVisible(false);


        nameTextField.setVisible(false);
        registerButton.setVisible(false);

        stateLabel.setVisible(false);

        hostTextField.setVisible(false);
        portTextField.setVisible(false);
        connectButton.setVisible(false);
    }

    private void showGameControls() {
        gameGrid.setVisible(true);
        subGrid.setVisible(true);
        timeSpentLabel.setVisible(true);
        stepCountLabel.setVisible(true);
        opponentsListView.setVisible(true);
        maxGameTimeLabel.setVisible(true);
        userNameLabel.setVisible(true);
        finishButton.setVisible(true);
    }

    private void showResultControls() {
        resultsListView.setVisible(true);
        exitButton.setVisible(true);
        resultLabel.setVisible(true);
        restartButton.setVisible(true);
    }

    @Override
    public void onTimerTick(int secondsFromStart) {
        timeSpentLabel.setText("Spent time: " + ++secondsFromStart);
        if(secondsFromStart == maxGameTime){
            this.client.finishGame();
        }
    }
}


