package com.sc.game.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CellTest {

    @Test
    void getType() {
        Cell cell = new Cell();
        Assertions.assertEquals(CellType.EMPTY, cell.getType());
    }

    @Test
    void setType() {
        Cell cell = new Cell();
        cell.setType(CellType.FILLED);
        Assertions.assertEquals(CellType.FILLED, cell.getType());
    }
}