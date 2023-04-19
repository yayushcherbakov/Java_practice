package com.sc.game.core;

/**
 * Ячейка поля Grid.
 */
public class Cell {
    private CellType type;

    /**
     * Базовый конструктор Cell.
     */
    public Cell() {
        this.type = CellType.EMPTY;
    }

    /**
     * Конструктор, задающий значение ячейке.
     *
     * @param type Тип ячейки.
     */
    public Cell(CellType type) {
        this.type = type;
    }

    /**
     * Возвращает тип ячейки.
     *
     * @return Тип ячейки
     */
    public CellType getType() {
        return type;
    }

    /**
     * Устанавливает тип ячейки.
     *
     * @param type Тип ячейки.
     */
    public void setType(CellType type) {
        this.type = type;
    }
}
