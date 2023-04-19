package game.core;

public class Cell {
    private CellType type;

    public CellType GetType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Cell() {
        this.type = CellType.EMPTY;
    }

    public Cell(CellType type) {
        this.type = type;
    }
}
