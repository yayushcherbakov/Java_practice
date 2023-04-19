package com.sc.game.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class BoardTest {

    @Test
    void getGrid() {
        ArrayList<ArrayList<Cell>> grid = new ArrayList<ArrayList<Cell>>();
        ArrayList<Cell> subgrid = new ArrayList<Cell>();
        subgrid.add(new Cell());
        grid.add(subgrid);

        int width = 1, height = 1;
        Board board = new Board(width, height);
        Assertions.assertEquals(grid.size(), board.getGrid().size());
        Assertions.assertEquals(grid.get(0).get(0).getType(), board.getGrid().get(0).get(0).getType());

        grid.get(0).get(0).setType(CellType.FILLED);
        CellType cellType = CellType.FILLED;
        board = new Board(width, height, cellType);
        Assertions.assertEquals(grid.size(), board.getGrid().size());
        Assertions.assertEquals(grid.get(0).get(0).getType(), board.getGrid().get(0).get(0).getType());
    }
}