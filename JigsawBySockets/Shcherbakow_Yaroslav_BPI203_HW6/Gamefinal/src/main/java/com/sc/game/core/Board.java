package com.sc.game.core;

import java.util.ArrayList;

public class Board {
    final private ArrayList<ArrayList<Cell>> grid;

    /**
     * Конструктор Board с пустой grid.
     *
     * @param width  Ширина grid.
     * @param height Высота grid.
     */
    public Board(int width, int height) {
        grid = new ArrayList<>();
        for (int i = 0; i < height; ++i) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < width; ++j) {
                grid.get(i).add(new Cell());
            }
        }
    }

    /**
     * Конструктор Board, заполняющий grid указанным типом ячеек.
     *
     * @param width           Ширина grid.
     * @param height          Высота grid.
     * @param defaultCellType Тип ячейки Cell.
     */
    public Board(int width, int height, CellType defaultCellType) {
        grid = new ArrayList<>();
        for (int i = 0; i < height; ++i) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < width; ++j) {
                grid.get(i).add(new Cell(defaultCellType));
            }
        }
    }

    /**
     * Метод, возвращающий grid;
     *
     * @return Сетка игрового поля.
     */
    public ArrayList<ArrayList<Cell>> getGrid() {
        return grid;
    }
}
