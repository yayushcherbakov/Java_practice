package game.core;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Cell>> grid;

    public ArrayList<ArrayList<Cell>> getGrid() {
        return grid;
    }

    public Board(int width, int height){
        grid = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < height; ++i) {
            grid.add(new ArrayList<Cell>());
            for (int j = 0; j < width; ++j) {
                grid.get(i).add(new Cell());
            }
        }
    }

    public Board(int width, int height, CellType defaultCellType){
        grid = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < height; ++i) {
            grid.add(new ArrayList<Cell>());
            for (int j = 0; j < width; ++j) {
                grid.get(i).add(new Cell(defaultCellType));
            }
        }
    }
}
