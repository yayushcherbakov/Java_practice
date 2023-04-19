package com.sc.game;

import game.core.Board;
import game.core.CellType;
import game.core.GameService;
import game.core.Position;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Timer;

public class GameController {
    @FXML
    public GridPane gameGrid;
    @FXML
    public GridPane subGrid;
    @FXML
    private Label timeSpentLabel;
    @FXML
    private Label stepCountLabel;
    @FXML
    private Button restartButton;
    @FXML
    private Button finishButton;

    @FXML
    protected void onRestartButtonClick() {
        if (isInitialState) {
            isInitialState = false;
            restartButton.setText("RESTART");
            finishButton.setVisible(true);
        }
        this.gameService.restart();

        if (gameGridCells != null) {
            gameGrid.getChildren().removeAll(gameGridCells);
        }
        if (subGridCells != null) {
            subGrid.getChildren().removeAll(subGridCells);
        }

        gameGridCells = createBoard(gameGrid, this.gameService.getBoard());
        subGridCells = createBoard(subGrid, this.gameService.getCurrentFigure().getBoard());
        makeDraggableNode(subGrid);


        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerHandler(timeSpentLabel), 0, 1000);

        refreshStepsCounter();
    }

    @FXML
    protected void onFinishButtonClick() {
        makeNotDraggableNode(subGrid);
        if (timer == null) {
            return;
        }
        timer.cancel();
        timer = null;
    }

    //для перемещения фигуры
    private double startX;
    private double startY;

    private Timer timer = null;
    private GameService gameService;

    private boolean isInitialState = true;

    private ArrayList<Rectangle> gameGridCells = null;
    private ArrayList<Rectangle> subGridCells = null;

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

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
                var color = getColorByCellType(board.getGrid().get(i).get(j).GetType(), (i + j/3*3) % 6 > 2);
                rectangle.setFill(color);

                GridPane.setMargin(rectangle, new Insets(1.5));
                grid.add(rectangle, j, i);

                cells.add(rectangle);
            }
        }

        return cells;
    }


    private Color getColorByCellType(CellType type, boolean isGreyEmpty) {
        switch (type) {
            case EMPTY:
                return isGreyEmpty ?  Color.rgb(235, 235, 235):Color.rgb(255, 255, 255);
            case FILLED:
                return Color.rgb(34, 64, 140);
            default:
                return Color.rgb(0, 0, 0, 0);
        }
    }

    private void setDefaultPosition(Node node) {
        node.setTranslateX(0);
        node.setTranslateY(0);
    }

    private Position subBoardClickPosition;

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
            if (gameBoardClickPosition.getX() != -1 && gameService.makeStep(subBoardClickPosition, gameBoardClickPosition)) {
                refreshGameBoard();
                refreshSubBoard();
                refreshStepsCounter();
            }
            setDefaultPosition(subGrid);
        });
    }

    private void makeNotDraggableNode(Node node) {
        node.setOnMousePressed(mouseEvent -> {
        });
        node.setOnMouseDragged(mouseEvent -> {
        });
        node.setOnMouseReleased(e -> {
        });
    }

    private Position getGameBoardRelativePosition(double cursorPositionX, double cursorPositionY) {
        for (int i = 0; i < this.gameGridCells.size(); i++) {
            var rectangle = this.gameGridCells.get(i);
            var bound = rectangle.localToScene(rectangle.getBoundsInLocal());
            if (cursorPositionX > bound.getMinX() && cursorPositionX < bound.getMaxX()
                    && cursorPositionY > bound.getMinY() && cursorPositionY < bound.getMaxY()) {
                return new Position(GridPane.getRowIndex(rectangle), GridPane.getColumnIndex(rectangle));
            }
        }
        return new Position(-1, -1);
    }

    private void setSubBoardRelativePosition(double cursorPositionX, double cursorPositionY) {
        for (int i = 0; i < this.subGridCells.size(); i++) {
            var rectangle = this.subGridCells.get(i);
            var bound = rectangle.localToScene(rectangle.getBoundsInLocal());

            if (cursorPositionX > bound.getMinX() && cursorPositionX < bound.getMaxX()
                    && cursorPositionY > bound.getMinY() && cursorPositionY < bound.getMaxY()) {
                subBoardClickPosition = new Position(GridPane.getRowIndex(rectangle), GridPane.getColumnIndex(rectangle));
                break;
            }
        }
    }

    private void refreshStepsCounter() {
        this.stepCountLabel.setText("Spent time: " + this.gameService.getStepCounts());
    }

    private void refreshGameBoard() {
        if (gameGridCells != null) {
            gameGrid.getChildren().removeAll(gameGridCells);
        }
        gameGridCells = createBoard(gameGrid, this.gameService.getBoard());
    }

    private void refreshSubBoard() {
        if (subGridCells != null) {
            subGrid.getChildren().removeAll(subGridCells);
        }
        subGridCells = createBoard(subGrid, this.gameService.getCurrentFigure().getBoard());
        makeDraggableNode(subGrid);
    }

}


